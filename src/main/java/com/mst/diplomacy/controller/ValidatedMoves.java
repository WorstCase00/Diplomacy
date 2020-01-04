package com.mst.diplomacy.controller;

import com.google.common.collect.Sets;
import com.mst.diplomacy.model.definition.Place;
import com.mst.diplomacy.model.definition.Player;
import com.mst.diplomacy.model.game.orders.ArmyMoveOrder;
import com.mst.diplomacy.model.game.orders.FleetMoveOrder;
import com.mst.diplomacy.model.game.orders.MoveTurnOrders;
import com.mst.diplomacy.model.game.orders.TroopMoveOrder;
import com.mst.diplomacy.rules.Hold;
import com.mst.diplomacy.rules.Move;
import com.mst.diplomacy.rules.Support;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidatedMoves {

    private final Set<Hold> holdOrders;
    private final Set<Move> moveOrders;
    private final Set<Support> supportOrders;
    private final Map<Player, MoveTurnOrders> playerMoves;
    private final Set<TroopMoveOrder> troopMoveOrders;

    public ValidatedMoves(Set<Hold> holdOrders, Set<Move> moveOrders, Set<Support> supportOrders, Map<Player, MoveTurnOrders> playerMoves, Set<TroopMoveOrder> troopMoveOrders) {
        this.holdOrders = holdOrders;
        this.moveOrders = moveOrders;
        this.supportOrders = supportOrders;
        this.playerMoves = playerMoves;
        this.troopMoveOrders = troopMoveOrders;
    }

    public static ValidatedMoves create(Map<Player, MoveTurnOrders> playerMoves) {
        Set<Hold> holds = Sets.newHashSet();
        Set<Move> moves = Sets.newHashSet();
        Set<Support> supports = Sets.newHashSet();
        Set<TroopMoveOrder> troopMoveOrders = Sets.newHashSet();
        playerMoves.values().stream().forEach(orders -> {
            orders.getHoldOrders().stream().forEach(holdOrder -> {
                holds.add(new Hold(holdOrder.getPlace().getName()));
            });
            orders.getArmyMoveOrders().stream().forEach(order -> {
                moves.add(new Move(order.getFrom().getName(), order.getTo().getName()));
                troopMoveOrders.add(order);
            });
            orders.getFleetMoveOrders().stream().forEach(order -> {
                moves.add(new Move(order.getFrom().getName(), order.getTo().getName()));
                troopMoveOrders.add(order);
            });
            orders.getSupportOrders().stream().forEach(order -> {
                supports.add(new Support(order.getFrom().getName(), order.getSupportFrom().getName(), order.getSupportTo().getName()));
            });
        });
        return new ValidatedMoves(holds, moves, supports, playerMoves, troopMoveOrders);
    }

    public Set<Hold> getHoldOrders() {
        return this.holdOrders;
    }

    public Set<Move> getMoveOrders() {
        return this.moveOrders;
    }

    public Set<Support> getSupportOrders() {
        return this.supportOrders;
    }

    public Map<Player, Set<ArmyMoveOrder>> getArmyMoves(Set<Move> successfulMoves) {
        return playerMoves.entrySet().stream().collect(Collectors.toMap(
                entry -> entry.getKey(),
                entry -> entry.getValue().getArmyMoveOrders().stream()
                        .filter(move -> successfulMoves.stream()
                                .filter(m -> m.getFrom().equals(move.getFrom().getName()) && m.getTo().equals(move.getTo().getName()))
                                .findFirst()
                                .isPresent())
                        .collect(Collectors.toSet())));
                
    }

    public Map<Player, Set<FleetMoveOrder>> getFleetMoves(Set<Move> successfulMoves) {
        return playerMoves.entrySet().stream().collect(Collectors.toMap(
                entry -> entry.getKey(),
                entry -> entry.getValue().getFleetMoveOrders().stream()
                        .filter(move -> successfulMoves.stream()
                                .filter(m -> m.getFrom().equals(move.getFrom().getName()) && m.getTo().equals(move.getTo().getName()))
                                .findFirst()
                                .isPresent())
                        .collect(Collectors.toSet())));
    }

    public Map<Player, Set<Place>> getDislodgements(Set<String> dislodged) {
        return Collections.emptyMap(); // TODO
    }

    public Set<TroopMoveOrder> getMoves(Set<Move> successfulMoves) {
        return troopMoveOrders.stream().filter(move -> successfulMoves.contains(new Move(move.getFrom().getName(), move.getTo().getName()))).collect(Collectors.toSet());
    }
}
