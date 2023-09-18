package com.wentware.test.graph;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of a directed 'acyclic' graph with named vertices and weighted edges
 * no enforcement of acyclic
 */
@Value
public class Graph {
    Set<Vertex> vertices = new HashSet<>();

    public Vertex addVertex(String id) {
        Optional<Vertex> vertexById = findVertexById(id);
        if (vertexById.isPresent()) {
            return vertexById.get();
        }
        Vertex vertexToAdd = new Vertex(id);
        vertices.add(vertexToAdd);
        return vertexToAdd;
    }

    public Optional<Vertex> findVertexById(String id) {
        return vertices
                .stream()
                .filter(v -> id.equals(v.getId()))
                .findFirst();
    }

    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @Value
    public static class Vertex {
        @EqualsAndHashCode.Include String id;
        Set<Edge> edges = new HashSet<>();

        public Vertex addEdgeTo(Vertex toVertex, int weight) {
            Edge edge = new Edge(this, toVertex, weight);
            edges.add(edge);
            return this;
        }

        Optional<Edge> findEdgeByToId(String toVertexId) {
            return getEdges()
                    .stream()
                    .filter(n -> n.toVertex().getId().equals(toVertexId))
                    .findFirst();
        }

        @Override
        public String toString() {
            return this.getId();
        }
    }

    public record Edge(
        Vertex fromVertex,
        Vertex toVertex,
        int weight) {}
}
