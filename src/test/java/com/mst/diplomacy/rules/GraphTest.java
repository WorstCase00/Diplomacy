package com.mst.diplomacy.rules;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class GraphTest {
    
    @Test
    public void testDepthFirst() {
        ResolutionGraph subject = new ResolutionGraph();
        subject.addNode("a");
        subject.addNode("b");
        subject.addNode("c");
        subject.addEdge("a", "b");
        subject.addEdge("b", "c");

        List<String> result = subject.getNodesDepthFirst();
    
        assertEquals(Lists.newArrayList("c", "b", "a"), result);
    }

    @Test
    public void testDepthFirstWithCycle() {
        ResolutionGraph subject = new ResolutionGraph();
        subject.addNode("a");
        subject.addNode("b");
        subject.addNode("c");  
        subject.addNode("d");
        subject.addEdge("a", "b");
        subject.addEdge("b", "c");
        subject.addEdge("c", "a");
        subject.addEdge("d", "a");

        List<String> result = subject.getNodesDepthFirst();

        assertEquals(Lists.newArrayList("c", "b", "a", "d"), result);
    }
}
