package com.wentware.test.app;

import com.wentware.test.graph.Graph;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.BiFunction;

public class AStar {

    Graph graph;
    BiFunction<Graph.Vertex, Graph.Vertex, Integer> heuristic;


    public AStar(Graph g, BiFunction<Graph.Vertex, Graph.Vertex, Integer> heuristic) {
        this.graph = g;
        this.heuristic = heuristic;
    }

    @AllArgsConstructor
    @Data
    class DVertexWrapper {
        Graph.Vertex vertex;
        Integer costFromSource;
        Integer heuristicCost;
        Graph.Vertex prevVertex;

        Integer totalCost() {
            return heuristicCost + costFromSource;
        }
    }

    Set<DVertexWrapper> visited = new HashSet<>();

    Optional<DVertexWrapper> findWrapperByVertex(Graph.Vertex vertex) {
        return visited
                .stream()
                .filter(v -> v.getVertex().equals(vertex))
                .findFirst();
    }

    PriorityQueue<DVertexWrapper> toVisit = new PriorityQueue<>(Comparator.comparingInt(DVertexWrapper::totalCost).reversed());
    public Solution solve(Graph.Vertex sourceVertex, Graph.Vertex goalVertex) {
        DVertexWrapper first = new DVertexWrapper(sourceVertex, 0, 0, null);
        toVisit.add(first);
        do {
            DVertexWrapper visiting = toVisit.remove();
            if (!visiting.getVertex().equals(goalVertex)) {
                visit(goalVertex, visiting);
                visited.add(visiting);
            } else {
                return foundSolution(visiting);
            }

        } while (!toVisit.isEmpty());
        return new Solution(0, null);
    }

    private Solution foundSolution(DVertexWrapper visiting) {
        System.out.println("Found goal vertex ");
        int cost = visiting.getCostFromSource();
        List<Graph.Vertex> path = new ArrayList<>();
        DVertexWrapper prev = visiting;
        path.add(visiting.getVertex());
        while (prev.getPrevVertex() != null) {
            prev = findWrapperByVertex(prev.getPrevVertex()).orElseThrow();
            path.add(prev.getVertex());
        }
        return new Solution(cost, path);
    }

    private void visit(Graph.Vertex goalVertex, DVertexWrapper visiting) {
        System.out.println("Visiting " + visiting.getVertex());

        for (Graph.Edge edge : visiting.getVertex().getEdges()) {
            DVertexWrapper toDVertex = new DVertexWrapper(
                    edge.toVertex(),
                    visiting.getCostFromSource() + edge.weight(),
                    heuristic.apply(goalVertex, edge.toVertex()),
                    visiting.getVertex());
            Optional<DVertexWrapper> revisitedOpt = findWrapperByVertex(edge.toVertex());
            if (revisitedOpt.isPresent()) {
                DVertexWrapper revisited = revisitedOpt.get();
                if (toDVertex.totalCost() < revisited.totalCost() ) {
                    toVisit.add(toDVertex);
                }
            } else {
                toVisit.add(toDVertex);
            }
        }
    }

    record Solution (int cost, List<Graph.Vertex> bestPath) {}
}
