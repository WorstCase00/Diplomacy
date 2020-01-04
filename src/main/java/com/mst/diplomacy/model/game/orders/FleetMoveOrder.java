package com.mst.diplomacy.model.game.orders;

import com.mst.diplomacy.model.definition.Place;
import com.mst.diplomacy.model.definition.Player;

import java.util.Objects;

public class FleetMoveOrder implements TroopMoveOrder {
    
    private final Player player;
    private final Place from;
    private final Place to;

    public FleetMoveOrder(Player player, Place from, Place to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Place getFrom() {
        return from;
    }

    @Override
    public Place getTo() {
        return to;
    }

    @Override
    public boolean isFleet() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FleetMoveOrder that = (FleetMoveOrder) o;
        return Objects.equals(player, that.player) &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, from, to);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FleetMoveOrder{");
        sb.append("player=").append(player);
        sb.append(", from=").append(from);
        sb.append(", to=").append(to);
        sb.append('}');
        return sb.toString();
    }
}
