package com.mst.diplomacy.model.game.orders;

import com.mst.diplomacy.model.definition.Place;
import com.mst.diplomacy.model.definition.Player;

public class HoldOrder {
    
    private final Player player;
    private final Place place;

    public HoldOrder(Player player, Place place) {
        this.player = player;
        this.place = place;
    }

    public Player getPlayer() {
        return player;
    }

    public Place getPlace() {
        return place;
    }
}
