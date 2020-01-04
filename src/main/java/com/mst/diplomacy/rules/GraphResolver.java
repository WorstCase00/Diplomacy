package com.mst.diplomacy.rules;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class GraphResolver implements Resolver {

    @Override
    public Result resolve(Set<Hold> holds, Set<Move> moves, Set<Support> supports) {

        ResolutionGraph graph = new ResolutionGraph();
        
        holds.stream().forEach(order -> {
            graph.addNode(order.getLocation());
            graph.addLoop(order.getLocation());
        });
        for (Move order : moves) {
            graph.addNode(order.getFrom());
            graph.addNode(order.getTo());
            graph.addEdge(order.getFrom(), order.getTo());
        }
        supports.stream().forEach(order -> {
            if (moves.contains(order.getMove()) && 
                    (!graph.containsNode(order.getFrom()) || graph.getSuccessors(order.getFrom()).equals(Sets.newHashSet(order.getSupportTo())))) {
                graph.incrementWeight(order.getSupportFrom(), order.getSupportTo());
            }
            graph.addNode(order.getFrom());
            graph.addEdge(order.getFrom(), order.getFrom());
        });
        
        


        Set<String> dislodged = Sets.newHashSet();
        Set<Move> success = Sets.newHashSet();
        graph.getNodesDepthFirst().stream().forEach(node -> {
            int maxAttack = 0;
            String maxAttackNode = null;
            for (Map.Entry<String, Integer> strengthEntry : graph.getSuccessorsWithWeights(node).entrySet()) {
                    int offense = strengthEntry.getValue();
                    if (maxAttack < offense) {
                        maxAttack = offense;
                        maxAttackNode = strengthEntry.getKey();
                    } else if (maxAttack == offense && maxAttackNode != null) {
                        graph.removeEdge(maxAttackNode, node);
                        graph.addEdge(maxAttackNode, maxAttackNode);
                        graph.removeEdge(strengthEntry.getKey(), node);
                        graph.addEdge(strengthEntry.getKey(), strengthEntry.getKey());
                        maxAttackNode = null;
                    } else {
                        graph.removeEdge(strengthEntry.getKey(), node);
                        graph.addEdge(strengthEntry.getKey(), strengthEntry.getKey());
                    }
            }

            if (maxAttackNode != null && maxAttackNode != node) {
                Optional<Integer> counterAttack = graph.getEdgeWeight(node, maxAttackNode);
                if (!counterAttack.isPresent() || counterAttack.get() < maxAttack) {
                    success.add(new Move(maxAttackNode, node));
                    if (graph.hasLoop(node)) {
                        dislodged.add(node);
                    }
                } else {
                    graph.removeEdge(maxAttackNode, node);
                    graph.addEdge(maxAttackNode, maxAttackNode);
                    // TODO increment
                    
                }
            }
        });

        System.out.println("SUCCESS");
        System.out.println(Joiner.on(",").join(success));
        return new Result(success, dislodged);
    }

}
