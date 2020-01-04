package com.mst.diplomacy.rules;

import java.util.Set;

public class Result {
    
    private final Set<Move> successfulMoves;
    private final Set<String> disloged;

    public Result(Set<Move> successfulMoves, Set<String> disloged) {
        this.successfulMoves = successfulMoves;
        this.disloged = disloged;
    }

    public Set<Move> getSuccessfulMoves() {
        return successfulMoves;
    }

    public Set<String> getDislodged() {
        return disloged;
    }
}
