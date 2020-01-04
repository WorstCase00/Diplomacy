package com.mst.diplomacy.ai;

import com.google.common.collect.Sets;
import com.mst.diplomacy.model.definition.Player;
import com.mst.diplomacy.model.game.GameState;
import com.mst.diplomacy.model.game.orders.TroopAdaptionOrder;
import com.mst.diplomacy.model.game.orders.HoldOrder;
import com.mst.diplomacy.model.game.orders.MoveTurnOrders;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class HoldingPlayer implements ControlledPlayer {
    
    private final Player player;

    public HoldingPlayer(Player player) {
        this.player = player;
    }

    public static HoldingPlayer init(Player player) {
        return new HoldingPlayer(player);
    }

    @Override
    public MoveTurnOrders getMoves(GameState gameState) {
        return new MoveTurnOrders(
                player,
                Sets.union(
                        gameState.getArmies(player).stream().map(army -> new HoldOrder(player, army)).collect(Collectors.toSet()),
                        gameState.getFleets(player).stream().map(fleet -> new HoldOrder(player, fleet)).collect(Collectors.toSet())),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet()
        );
    }

    @Override
    public Set<TroopAdaptionOrder> getBuilds(GameState gameState) {
        return Collections.emptySet(); // can never happen
    }
}
