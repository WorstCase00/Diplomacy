package com.mst.diplomacy.model.game;

import com.mst.diplomacy.model.definition.Place;
import com.mst.diplomacy.model.definition.Player;
import com.mst.diplomacy.model.game.orders.TroopMoveOrder;

import java.util.Set;

public interface UiGameListener {
    
    void move(Set<TroopMoveOrder> moveOrders);

    @Deprecated
    void addArmy(Place place, Player player);

    @Deprecated
    void addFleet(Place place, Player player);

    @Deprecated
    void removeTroops(Place place, Player player);

    void playerOccupies(Place land, Player player);

}
