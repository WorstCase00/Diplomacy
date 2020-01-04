package com.mst.diplomacy.model.game.orders;

import com.mst.diplomacy.model.definition.Place;
import com.mst.diplomacy.model.definition.Player;

public interface TroopMoveOrder {


    Player getPlayer();

    Place getFrom();

    Place getTo();
    
    boolean isFleet();
}
