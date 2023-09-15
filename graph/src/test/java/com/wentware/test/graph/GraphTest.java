package com.wentware.test.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphTest {

    @Test
    void addNOdeById_withDup_shouldNotAddDup() {
        Graph graph = new Graph();
        graph.addNode("V1");
        graph.addNode("V1");

        assertEquals(1, graph.getNodes().size());
    }

    @Test
    void addEdges() {
        Graph graph = new Graph();
        Graph.Node n1 = graph.addNode("N1");
        Graph.Node n2 = graph.addNode("N2");
        Graph.Node n3 = graph.addNode("N3");

        Graph.Node edgeFromN1ToN2 = n1.addEdgeTo(n2, 3);
        Graph.Node edgeFromN1ToN3 = n1.addEdgeTo(n3, 1);

        assertEquals(3, graph.getNodes().size());
        assertEquals(2, n1.getEdges().size());
        assertEdge(n1, "N2", 3);
        assertEdge(n1, "N3", 1);
    }

    @Test
    void findNodeById() {
        Graph graph = new Graph();

        graph.addNode("N1");
        graph.addNode("N2");

        assertTrue(graph.findNodeById("N1").isPresent());
        assertNotNull(graph.findNodeById("N2").orElseThrow());
        assertTrue(graph.findNodeById("N3").isEmpty());
    }

    private void assertEdge(Graph.Node fromNode, String toNodeId, int expectedWeight) {
        Graph.Edge edge = fromNode.findEdgeByToId(toNodeId).orElseThrow();
        assertEquals(fromNode, edge.getFromNode());
        assertEquals(toNodeId, edge.getToNode().getId());
        assertEquals(expectedWeight, edge.getWeight());
    }

}