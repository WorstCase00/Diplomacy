package com.mst.diplomacy.model.game;

import com.mst.diplomacy.model.definition.Place;
import com.mst.diplomacy.model.definition.Player;
import com.mst.diplomacy.model.game.orders.ArmyMoveOrder;
import com.mst.diplomacy.model.game.orders.FleetMoveOrder;

import java.util.Map;
import java.util.Set;

public class MoveTurnResult {

    private final Map<Player, Set<ArmyMoveOrder>> armyMoves;
    private final Map<Player, Set<FleetMoveOrder>> fleetMoves;
    private final Map<Player, Set<Place>> retreats;

    public MoveTurnResult(Map<Player, Set<ArmyMoveOrder>> armyMoves, Map<Player, Set<FleetMoveOrder>> fleetMoves, Map<Player, Set<Place>> retreats) {
        this.armyMoves = armyMoves;
        this.fleetMoves = fleetMoves;
        this.retreats = retreats;
    }

    public Map<Player, Set<ArmyMoveOrder>> getSuccessfulArmyMoves() {
        return this.armyMoves;
    }

    public Map<Player, Set<FleetMoveOrder>> getSuccessfulFleetMoves() {
        return this.fleetMoves;
    }

    public Map<Player, Set<Place>> getRetreats() {
        return this.retreats;
    }
}
