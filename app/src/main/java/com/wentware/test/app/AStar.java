package com.wentware.test.app;

import com.wentware.test.graph.Graph;

import java.util.List;

public class AStar {

    Graph graph;
    public AStar(Graph g) {
        this.graph = g;
    }

    public Solution solve() {
        return new Solution(0, null);
    }

    record Solution (int cost, List<Graph.Vertex> bestPath) {}
}
