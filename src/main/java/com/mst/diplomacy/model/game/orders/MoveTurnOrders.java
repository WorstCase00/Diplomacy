package com.mst.diplomacy.model.game.orders;

import com.mst.diplomacy.model.definition.Player;

import java.util.Set;

public class MoveTurnOrders {
    
    private final Player player;
    private final Set<HoldOrder> holdOrders;
    private final Set<ArmyMoveOrder> armyMoveOrders;
    private final Set<FleetMoveOrder> fleetMoveOrders;
    private final Set<SupportOrder> supportOrders;

    public MoveTurnOrders(
            Player player, 
            Set<HoldOrder> holdOrders, 
            Set<ArmyMoveOrder> armyMoveOrders, 
            Set<FleetMoveOrder> fleetMoveOrders, 
            Set<SupportOrder> supportOrders) {
        this.player = player;
        this.holdOrders = holdOrders;
        this.armyMoveOrders = armyMoveOrders;
        this.fleetMoveOrders = fleetMoveOrders;
        this.supportOrders = supportOrders;
    }

    public Set<FleetMoveOrder> getFleetMoveOrders() {
        return fleetMoveOrders;
    }

    public Set<ArmyMoveOrder> getArmyMoveOrders() {
        return armyMoveOrders;
    }

    public Player getPlayer() {
        return player;
    }

    public Set<HoldOrder> getHoldOrders() {
        return holdOrders;
    }


    public Set<SupportOrder> getSupportOrders() {
        return supportOrders;
    }
}
