package com.wentware.test.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GraphTest {

    @Test
    void addVertexById_withDup_shouldNotAddDup() {
        Graph graph = new Graph();
        graph.addVertex("V1");
        graph.addVertex("V1");

        assertEquals(1, graph.getVertices().size());
    }

    @Test
    void addEdges() {
        Graph graph = new Graph();
        Graph.Vertex n3 = graph.addVertex("N3");
        Graph.Vertex n2 = graph.addVertex("N2");
        Graph.Vertex n1 = graph.addVertex("N1")
                .addEdgeTo(n2, 3)
                .addEdgeTo(n3, 1);

        assertEquals(3, graph.getVertices().size());
        assertEquals(2, n1.getEdges().size());
        Graph.Edge edgeFromN1ToN2 = n1.findEdgeByToId("N2").orElseThrow();
        assertEdge(n1, "N2", 3, edgeFromN1ToN2);
        Graph.Edge edgeFromN1ToN3 = n1.findEdgeByToId("N3").orElseThrow();
        assertEdge(n1, "N3", 1, edgeFromN1ToN3);
    }

    @Test
    void findVertexById() {
        Graph graph = new Graph();

        graph.addVertex("N1");
        graph.addVertex("N2");

        assertTrue(graph.findVertexById("N1").isPresent());
        assertNotNull(graph.findVertexById("N2").orElseThrow());
        assertTrue(graph.findVertexById("N3").isEmpty());
    }

    private void assertEdge(Graph.Vertex expectedFromVertex, String expectedToVertexId, int expectedWeight, Graph.Edge edge) {
        assertEquals(expectedFromVertex, edge.fromVertex());
        assertEquals(expectedToVertexId, edge.toVertex().getId());
        assertEquals(expectedWeight, edge.weight());
    }

}