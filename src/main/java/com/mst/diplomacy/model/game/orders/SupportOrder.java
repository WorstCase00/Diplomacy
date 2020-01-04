package com.mst.diplomacy.model.game.orders;

import com.mst.diplomacy.model.definition.Place;
import com.mst.diplomacy.model.definition.Player;

public class SupportOrder {

    private final Player player;
    private final Place from;
    private final Place supportFrom;
    private final Place supportTo;

    public SupportOrder(Player player, Place from, Place supportFrom, Place supportTo) {
        this.player = player;
        this.from = from;
        this.supportFrom = supportFrom;
        this.supportTo = supportTo;
    }

    public Player getPlayer() {
        return player;
    }

    public Place getFrom() {
        return from;
    }

    public Place getSupportFrom() {
        return supportFrom;
    }

    public Place getSupportTo() {
        return supportTo;
    }
}
