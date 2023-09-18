package com.wentware.test.app;

import com.wentware.test.graph.Graph;
import com.wentware.test.graph.GraphParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class AStarTest {

    @Test
    void solve() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("testgraph.csv").getFile());
        InputStream inputStream = new FileInputStream(file);
        Graph graph = GraphParser.parse(inputStream);

        AStar aStar = new AStar(graph, EucHeuristicCalc.getHeuristic());

        AStar.Solution solution = aStar.solve(graph.findVertexById("B0").orElseThrow(), graph.findVertexById("B4").orElseThrow());

        assertNotNull(solution);
    }

}