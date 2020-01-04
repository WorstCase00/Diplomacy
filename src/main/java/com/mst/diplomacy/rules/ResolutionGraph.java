package com.mst.diplomacy.rules;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.graphstream.graph.DepthFirstIterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import sun.jvm.hotspot.jdi.ShortTypeImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ResolutionGraph {

    private final Graph graph;
    private static final String DEFENSE = "defense";

    public ResolutionGraph() {
        this.graph = new DefaultGraph("");
    }

    public void addNode(String location) {
        Node destinationNode = graph.getNode(location);
        if (destinationNode == null) {
            graph.addNode(location);
        }
    }

    public void addLoop(String location) {
        addEdge(location, location);
    }

    public void addEdge(String from, String to) {
        Edge edge = graph.addEdge(from + to, from, to, true);
        edge.setAttribute(DEFENSE, 1);
    }
    
    public List<String> getNodesDepthFirst() {
        
        List<String> list = Lists.newArrayList();
        Set<String> visited = Sets.newHashSet();
        graph.getNodeSet().stream().forEach(node -> {
            List<String> nextList = Lists.newArrayList();
            DepthFirstIterator depthFirstIterator = new DepthFirstIterator(node, true);
            while (depthFirstIterator.hasNext()) {
                Node next = depthFirstIterator.next();
                if (!visited.contains(next.getId())) {
                    visited.add(next.getId());
                    nextList.add(next.getId());
                }
            }
            Collections.reverse(nextList);
            list.addAll(nextList);
        });
        return list;
    }


    public Map<String, Integer> getSuccessorsWithWeights(String node) {
        Map<String, Integer> mapping = Maps.newHashMap();
        graph.getNode(node).getEachEnteringEdge().forEach(edge -> {
            mapping.put(edge.getSourceNode().getId(), edge.getAttribute(DEFENSE));
        });
        return mapping;
    }

    public Optional<Integer> getEdgeWeight(String node, String maxAttackNode) {
        Edge edge = graph.getNode(node).getEdgeBetween(maxAttackNode);
        if (edge != null) {
            return Optional.of(edge.getAttribute(DEFENSE));
        }
        return Optional.empty();
    }

    public boolean containsNode(String id) {
        return graph.getNode(id) != null;
    }

    public void incrementWeight(String supportFrom, String supportTo) {
        Edge edge = graph.getNode(supportFrom).getEdgeBetween(supportTo);
        edge.setAttribute(DEFENSE, (int) edge.getAttribute(DEFENSE) + 1);
    }

    public boolean hasLoop(String node) {
        return graph.getNode(node).getEdgeBetween(node) != null;
    }

    public Set<String> getSuccessors(String node) {

        Set<String> incomingNodes = Sets.newHashSet();
        graph.getNode(node).getEachEnteringEdge().forEach(edge -> {
            incomingNodes.add(edge.getSourceNode().getId());
        });
        return incomingNodes;
    }

    public void removeEdge(String from, String to) {
        graph.removeEdge(from, to);
    }
}
