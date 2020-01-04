package com.mst.diplomacy.rules;

import java.util.Set;

public interface Resolver {
    Result resolve(Set<Hold> holds, Set<Move> moves, Set<Support> supports);
}
