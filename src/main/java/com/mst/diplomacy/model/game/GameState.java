package com.mst.diplomacy.model.game;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mst.diplomacy.model.definition.Game;
import com.mst.diplomacy.model.definition.Place;
import com.mst.diplomacy.model.definition.Player;
import com.mst.diplomacy.model.game.orders.ArmyMoveOrder;
import com.mst.diplomacy.model.game.orders.FleetMoveOrder;

import javax.annotation.concurrent.Immutable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Immutable
public class GameState {

    private final Map<Player, Set<Place>> armyPlaces;
    private final Map<Player, Set<Place>> fleetPlaces;
    private final Map<Place, Player> capitalOwners;
    private final Map<Player, Set<Place>> toRetreat;
    private final int moveTurnCount;

    public GameState(
            Map<Player, Set<Place>> armyPlaces,
            Map<Player, Set<Place>> fleetPlaces,
            Map<Place, Player> capitalOwners, 
            Map<Player, Set<Place>> toRetreat,
            int moveTurnCount) {
        this.armyPlaces = armyPlaces;
        this.fleetPlaces = fleetPlaces;
        this.capitalOwners = capitalOwners;
        this.toRetreat = toRetreat;
        this.moveTurnCount = moveTurnCount;
        assertValidity();
    }

    private void assertValidity() {
        Set<Place> uniqueTroopLocations = Sets.newHashSet();
        for (Set<Place> places : armyPlaces.values()) {
            for (Place place : places) {
                Preconditions.checkArgument(!uniqueTroopLocations.contains(place), place);
                uniqueTroopLocations.add(place);
            }
        }
        for (Set<Place> places : fleetPlaces.values()) {
            for (Place place : places) {
                Preconditions.checkArgument(!uniqueTroopLocations.contains(place), place);
                uniqueTroopLocations.add(place);
            }
        }
    }

    public Set<Place> getArmies(Player player) {
        return armyPlaces.get(player);
    }

    public Set<Place> getFleets(Player player) {
        return fleetPlaces.get(player);
    }

    public static GameState init(Game game) {
        Map<Place, Player> ownedCapitals = Maps.newHashMap();
        for (Player player : game.getPlayers()) {
            player.getHomePlaces().stream().filter(Place::isCapital).forEach(capital -> ownedCapitals.put(capital, player));
        }
        return new GameState(
                game.getPlayers().stream().collect(Collectors.toMap(Function.identity(), Player::getArmyPlaces)),
                game.getPlayers().stream().collect(Collectors.toMap(Function.identity(), Player::getFleetPlaces)),
                ownedCapitals,
                Collections.emptyMap(), 
                0);
    }

    public GameState moveTurnResolved(MoveTurnResult moveTurnResult) {
        Map<Player, Set<Place>> armyPlaces = adaptArmyLocations(moveTurnResult.getSuccessfulArmyMoves(), this.armyPlaces);
        Map<Player, Set<Place>> fleetPlaces = adaptTroopLocations(moveTurnResult.getSuccessfulFleetMoves(), this.fleetPlaces);
        return new GameState(
                armyPlaces,
                fleetPlaces,
                adaptCapitalOwnership(this.capitalOwners, armyPlaces, fleetPlaces),
                moveTurnResult.getRetreats(),
                moveTurnCount + 1);
    }

    private static Map<Place, Player> adaptCapitalOwnership(Map<Place, Player> oldCapitalOwnership, Map<Player, Set<Place>> armyPlaces, Map<Player, Set<Place>> fleetPlaces) {
        Map<Place, Player> newCapitalOwnership = Maps.newHashMap(oldCapitalOwnership);
        armyPlaces.entrySet().stream().forEach(troopLocations -> {
            troopLocations.getValue().stream().filter(Place::isCapital).forEach(capital -> newCapitalOwnership.put(capital, troopLocations.getKey()));
        });
        fleetPlaces.entrySet().stream().forEach(troopLocations -> {
            troopLocations.getValue().stream().filter(Place::isCapital).forEach(capital -> newCapitalOwnership.put(capital, troopLocations.getKey()));
        });
        return newCapitalOwnership;
    }

    private Map<Player, Set<Place>> adaptArmyLocations(Map<Player, Set<ArmyMoveOrder>> moves, Map<Player, Set<Place>> places) {
        return places.entrySet().stream().collect(Collectors.toMap(
                entry -> entry.getKey(),
                entry -> {
                    if (!moves.containsKey(entry.getKey())) {
                        return entry.getValue();
                    }
                    return Sets.union(
                            Sets.difference(entry.getValue(), moves.get(entry.getKey()).stream().map(ArmyMoveOrder::getFrom).collect(Collectors.toSet())),
                            moves.get(entry.getKey()).stream().map(ArmyMoveOrder::getTo).collect(Collectors.toSet()));
                } 
                        
                        
        ));
    }

    private Map<Player, Set<Place>> adaptTroopLocations(Map<Player, Set<FleetMoveOrder>> moves, Map<Player, Set<Place>> places) {
        return places.entrySet().stream().collect(Collectors.toMap(
                entry -> entry.getKey(),
                entry -> {
                    if (!moves.containsKey(entry.getKey())) {
                        return entry.getValue();
                    }
                    return Sets.union(
                            Sets.difference(entry.getValue(), moves.get(entry.getKey()).stream().map(FleetMoveOrder::getFrom).collect(Collectors.toSet())),
                            moves.get(entry.getKey()).stream().map(FleetMoveOrder::getTo).collect(Collectors.toSet()));
                }
        ));
    }

    public GameState retreatTurnResult(RetreatTurnResult retreatTurnResult) {
        return this;
    }

    public GameState buildTurnResult(BuildTurnResult buildTurnResult) {
        Map<Player, Set<Place>> newArmyPlaces = Maps.newHashMap(armyPlaces);
        Map<Player, Set<Place>> newFleetPlaces = Maps.newHashMap(fleetPlaces);
        buildTurnResult.getTroopAdaptionOrders().stream().forEach(order -> {
            if (order.isBuild()) {
                if (order.isFleet()) {
                    Set<Place> oldPlaces = Sets.newHashSet(fleetPlaces.get(order.getPlayer()));
                    oldPlaces.add(order.getPlace());
                    newFleetPlaces.put(order.getPlayer(), oldPlaces);
                } else {
                    Set<Place> oldPlaces = Sets.newHashSet(armyPlaces.get(order.getPlayer()));
                    oldPlaces.add(order.getPlace());
                    newArmyPlaces.put(order.getPlayer(), oldPlaces);
                }
                
            } else {
                Set<Place> oldPlaces = Sets.newHashSet(armyPlaces.get(order.getPlayer()));
                oldPlaces.remove(order.getPlace());
                newArmyPlaces.put(order.getPlayer(), oldPlaces);

                Set<Place> oldFleetPlaces = Sets.newHashSet(fleetPlaces.get(order.getPlayer()));
                oldFleetPlaces.remove(order.getPlace());
                newFleetPlaces.put(order.getPlayer(), oldFleetPlaces);
            }
        });
        
        return new GameState(
                newArmyPlaces,
                newFleetPlaces,
                this.capitalOwners,
                this.toRetreat,
                moveTurnCount);
    }

    public boolean isBuildStepNext() {
        return moveTurnCount % 2 == 1;
    }

    public Map<Place, Player> getCapitalOwners() {
        return capitalOwners;
    }

    public Set<Place> getTroopLocations(Player player) {
        return Sets.union(getArmies(player), getFleets(player));
    }

    public int getTroopDiscrepancy(Player player) {
        return getCapitalCount(player) - getTroopCount(player);
    }

    private int getCapitalCount(Player player) {
        return (int) capitalOwners.entrySet().stream().filter(entry -> entry.getValue().equals(player)).count();
    }

    private int getTroopCount(Player player) {
        return getTroopLocations(player).size();
    }
    
    public boolean isOccupied(Place place) {
        for (Set<Place> troopPlaces : armyPlaces.values()) {
            if (troopPlaces.contains(place)) {
                return true;
            }
        }
        for (Set<Place> troopPlaces : fleetPlaces.values()) {
            if (troopPlaces.contains(place)) {
                return true;
            }
        }
        return false;
    }

    public Set<Place> getUnoccupiedHomeCapitals(Player player) {
        return player.getHomeCapitals().stream().filter(capital -> !isOccupied(capital)).collect(Collectors.toSet());
    }

    public boolean isGameOver() {
        return false;
    }
}
