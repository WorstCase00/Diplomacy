package com.mst.diplomacy.ai;

import com.mst.diplomacy.model.game.GameState;
import com.mst.diplomacy.model.game.orders.TroopAdaptionOrder;
import com.mst.diplomacy.model.game.orders.MoveTurnOrders;

import java.util.Set;

public interface ControlledPlayer {
    MoveTurnOrders getMoves(GameState gameState);

    Set<TroopAdaptionOrder> getBuilds(GameState gameState);
}
