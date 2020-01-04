package com.mst.diplomacy.ai;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mst.diplomacy.model.definition.Place;
import com.mst.diplomacy.model.definition.Player;
import com.mst.diplomacy.model.definition.World;
import com.mst.diplomacy.model.game.GameState;
import com.mst.diplomacy.model.game.orders.ArmyMoveOrder;
import com.mst.diplomacy.model.game.orders.TroopAdaptionOrder;
import com.mst.diplomacy.model.game.orders.FleetMoveOrder;
import com.mst.diplomacy.model.game.orders.MoveTurnOrders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class RandomMovePlayer implements ControlledPlayer {
    
    private final Player player;
    private final World world;
    private final java.util.Random random = new Random();

    public RandomMovePlayer(Player player, World world) {
        this.player = player;
        this.world = world;
    }

    public static RandomMovePlayer init(Player player, World world) {
        return new RandomMovePlayer(player, world);
    }

    @Override
    public MoveTurnOrders getMoves(GameState gameState) {
        Set<Place> armies = gameState.getArmies(player);
        Set<ArmyMoveOrder> armyMoves = armies.stream().map(place -> new ArmyMoveOrder(player, place, pick(world.getAdjacentLandPlaces(place)))).collect(Collectors.toSet());
        Set<FleetMoveOrder> fleetMoves = gameState.getFleets(player).stream().map(place -> new FleetMoveOrder(player, place, pick(world.getAdjacentSeasAndCoasts(place)))).collect(Collectors.toSet());
        System.out.println("MOVES");
        System.out.println(Joiner.on(",").join(armyMoves));
        System.out.println(Joiner.on(",").join(fleetMoves));
        return new MoveTurnOrders(
                player,
                Collections.emptySet(),
                armyMoves,
                fleetMoves,
                Collections.emptySet()
        );
    }

    @Override
    public Set<TroopAdaptionOrder> getBuilds(GameState gameState) {
        int discrepancy = gameState.getTroopDiscrepancy(this.player);
        if (discrepancy > 0) {
            Set<Place> possibleBuilds = gameState.getUnoccupiedHomeCapitals(this.player);
            List<Place> list = Lists.newArrayList(possibleBuilds);
            Collections.shuffle(list);
            Set<TroopAdaptionOrder> orders = list.stream().limit(discrepancy).map(capital -> {
                        if (world.isLandLocked(capital) || this.random.nextBoolean()) {
                            return TroopAdaptionOrder.buildArmy(capital, this.player);
                        }
                        return TroopAdaptionOrder.buildFleet(capital, this.player);
                    }
            ).collect(Collectors.toSet());
            return orders;
        } else if (discrepancy < 0) {
            Set<Place> possibleBuilds = gameState.getTroopLocations(this.player);
            List<Place> list = Lists.newArrayList(possibleBuilds);
            Collections.shuffle(list);
            Set<TroopAdaptionOrder> orders = list.stream().limit(Math.abs(discrepancy)).map(capital -> TroopAdaptionOrder.destroy(capital, this.player)).collect(Collectors.toSet());
            return orders;
        }
        return Collections.emptySet();
    }

    private Place pick(Set<Place> adjacentLandPlaces) {
        ArrayList<Place> places = Lists.newArrayList(adjacentLandPlaces);
        Collections.shuffle(places);
        return places.stream().findFirst().get();
    }
}
