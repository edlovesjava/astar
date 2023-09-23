package com.wentware.test.app;

import com.wentware.test.graph.Graph;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;

@Log4j2
public class AStar {
    Graph graph;
    Function<String, Integer> heuristic;
    PriorityQueue<DVertexWrapper> toVisit = new PriorityQueue<>(Comparator.comparingInt(DVertexWrapper::totalCost));
    Set<DVertexWrapper> visited = new HashSet<>();

    public AStar(Graph g, Function<String, Integer> heuristic) {
        this.graph = g;
        this.heuristic = heuristic;
    }

    Optional<DVertexWrapper> findRevisitedByVertex(Graph.Vertex vertex) {
        return visited
                .stream()
                .filter(v -> v.getVertex().equals(vertex))
                .findFirst();
    }

    public Solution solve(Graph.Vertex sourceVertex, Graph.Vertex goalVertex) {
        DVertexWrapper first = new DVertexWrapper(sourceVertex, 0, 0, null);
        toVisit.add(first);
        do {
            DVertexWrapper visiting = toVisit.remove();
            if (visiting.getVertex().equals(goalVertex)) {
                return buildSolution(visiting);
            }
            visitEdgesOf(visiting);
            visited.add(visiting);
        } while (!toVisit.isEmpty());
        return new Solution(0, null); //failed to find
    }

    private Solution buildSolution(DVertexWrapper visiting) {
        log.info("Found goal vertex ");
        int cost = visiting.getCostFromSource();
        List<Graph.Vertex> path = new ArrayList<>();
        DVertexWrapper prev = visiting;
        path.add(visiting.getVertex());
        while (prev.getPrevVertex() != null) {
            prev = findRevisitedByVertex(prev.getPrevVertex()).orElseThrow();
            path.add(prev.getVertex());
        }
        return new Solution(cost, path);
    }

    private void visitEdgesOf(DVertexWrapper visiting) {
        log.info("Visiting " + visiting.getVertex());

        for (Graph.Edge edge : visiting.getVertex().getEdges()) {
            Integer heuristicVal = heuristic.apply(edge.toVertex().getId());
            DVertexWrapper toDVertex = new DVertexWrapper(
                    edge.toVertex(),
                    visiting.getCostFromSource() + edge.weight(),
                    heuristicVal,
                    visiting.getVertex());
            Optional<DVertexWrapper> revisitedOpt = findRevisitedByVertex(edge.toVertex());
            if (revisitedOpt.isPresent()) {
                DVertexWrapper revisited = revisitedOpt.get();
                if (toDVertex.totalCost() < revisited.totalCost()) {
                    toVisit.add(toDVertex);
                }
            } else {
                toVisit.add(toDVertex);
            }
        }
    }

    @AllArgsConstructor
    @Data
    private static class DVertexWrapper {
        Graph.Vertex vertex;
        Integer costFromSource;
        Integer heuristicCost;
        Graph.Vertex prevVertex;

        Integer totalCost() {
            return heuristicCost + costFromSource;
        }
    }

    record Solution(int cost, List<Graph.Vertex> bestPath) {
    }
}
