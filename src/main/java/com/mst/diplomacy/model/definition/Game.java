package com.mst.diplomacy.model.definition;

import java.util.Set;

public class Game {
    
    private final World world;
    private final Set<Player> players;

    public Game(World world, Set<Player> players) {
        this.world = world;
        this.players = players;
    }

    public World getWorld() {
        return world;
    }

    public Set<Player> getPlayers() {
        return players;
    }
}
