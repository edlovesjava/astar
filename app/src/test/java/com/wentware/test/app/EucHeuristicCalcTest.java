package com.wentware.test.app;

import com.wentware.test.graph.Graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EucHeuristicCalcTest {

    @Test
    void testHeuristic() {
        Graph g = new Graph();
        Graph.Vertex fromV = g.addVertex("B0");
        Graph.Vertex toV = g.addVertex("E4");
        int h = EucHeuristicCalc.getHeuristic().apply(fromV, toV);
        assertEquals(5, h);
    }

    @Test
    void testComputeX() {
        String id = "B1";
        Integer x = EucHeuristicCalc.computeX(id);
        assertEquals(1, x);
    }

    @Test
    void testComputeY() {
        String id = "B1";
        Integer y = EucHeuristicCalc.computeY(id);
        assertEquals(1, y);
    }

}
