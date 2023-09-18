package com.wentware.test.app;

import com.wentware.test.graph.Graph;

import java.util.function.BiFunction;

public class EucHeuristicCalc {
    private EucHeuristicCalc() {
        //static members only
    }

    static BiFunction<Graph.Vertex, Graph.Vertex, Integer> heuristic = (fromV, toV) -> {
        int fromX = computeX(fromV.getId());
        int fromY = computeY(fromV.getId());
        int toX = computeX(toV.getId());
        int toY = computeY(toV.getId());

        int dx = toX - fromX;
        int dy = toY - fromY;

        double sqrt = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        return Math.toIntExact(Math.round(sqrt));
    };


    public static BiFunction<Graph.Vertex, Graph.Vertex, Integer> getHeuristic() {
        return heuristic;
    }

    static Integer computeX(String id) {
        return Integer.parseInt(id.substring(1, 2));
    }

    static Integer computeY(String id) {
        return "ABCDE".indexOf(id.substring(0, 1));
    }
}