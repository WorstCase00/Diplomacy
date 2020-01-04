package com.mst.diplomacy.model.game;

import com.google.common.collect.Sets;
import com.mst.diplomacy.model.game.orders.TroopAdaptionOrder;
import com.mst.diplomacy.model.game.orders.DestroyOrder;

import java.util.Set;

public class BuildTurnResult {
    
    private final Set<TroopAdaptionOrder> troopAdaptionOrders;

    public BuildTurnResult(Set<TroopAdaptionOrder> troopAdaptionOrders) {
        this.troopAdaptionOrders = troopAdaptionOrders;
    }

    public Set<TroopAdaptionOrder> getTroopAdaptionOrders() {
        return troopAdaptionOrders;
    }

    public static class Builder {

        private Set<TroopAdaptionOrder> troopAdaptionOrders = Sets.newHashSet();

        public BuildTurnResult build() {
            return new BuildTurnResult(troopAdaptionOrders);
        }

        public void add(Set<TroopAdaptionOrder> builds) {
            this.troopAdaptionOrders.addAll(builds);
        }
    }
}
