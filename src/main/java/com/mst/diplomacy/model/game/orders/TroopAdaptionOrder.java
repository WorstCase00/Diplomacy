package com.mst.diplomacy.model.game.orders;

import com.mst.diplomacy.model.definition.Place;
import com.mst.diplomacy.model.definition.Player;

public class TroopAdaptionOrder {
    
    private final Place place;
    private final Player player;
    private final boolean build;
    private final boolean fleet;

    public Place getPlace() {
        return place;
    }

    public boolean isBuild() {
        return build;
    }

    public Player getPlayer() {
        return this.player;
    }

    private TroopAdaptionOrder(Place place, Player player, boolean build, boolean fleet) {
        this.place = place;
        this.player = player;
        this.build = build;
        this.fleet = fleet;
    }

    public static TroopAdaptionOrder buildArmy(Place capital, Player player) {
        return new TroopAdaptionOrder(capital, player, true, false);
    }
    
    public static TroopAdaptionOrder buildFleet(Place capital, Player player) {
        return new TroopAdaptionOrder(capital, player, true, true);
    }

    public static TroopAdaptionOrder destroy(Place place, Player player) {
        return new TroopAdaptionOrder(place, player, false, false);
    }

    public boolean isFleet() {
        return fleet;
    }
}
