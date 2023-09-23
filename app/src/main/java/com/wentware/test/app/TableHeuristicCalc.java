package com.wentware.test.app;

import java.util.Map;
import java.util.function.Function;

public class TableHeuristicCalc {
    Map<String, Integer> vertexToHeuristicMap;
    Function<String, Integer> heuristic = toV -> this.vertexToHeuristicMap.get(toV);

    public TableHeuristicCalc(Map<String, Integer> vertexToHeuristicMap) {
        this.vertexToHeuristicMap = vertexToHeuristicMap;
    }

    public Function<String, Integer> getHeuristic() {
        return heuristic;
    }
}