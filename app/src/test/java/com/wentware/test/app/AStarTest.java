package com.wentware.test.app;

import com.wentware.test.graph.Graph;
import com.wentware.test.graph.GraphParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class AStarTest {

    @Test
    void solve() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("testgraph.csv").getFile());
        InputStream inputStream = new FileInputStream(file);
        Graph graph = GraphParser.parse(inputStream);

        AStar aStar = new AStar(graph);

        assertNotNull(aStar);
    }

}