package com.mst.diplomacy.model.game.orders;

import com.mst.diplomacy.model.definition.Place;

import java.util.Objects;

public class DestroyOrder {
    
    private final Place place;

    public DestroyOrder(Place place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DestroyOrder that = (DestroyOrder) o;
        return Objects.equals(place, that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(place);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DestroyOrder{");
        sb.append("place=").append(place);
        sb.append('}');
        return sb.toString();
    }
}
