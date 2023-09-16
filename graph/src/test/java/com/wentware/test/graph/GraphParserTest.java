package com.wentware.test.graph;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GraphParserTest {

    static final String[] GRAPH_DEF = {
            "N1,N2,3",
            "N1,N3,4",
            "N2,N3,2",
            "N2,N4,1",
            "N3,N4,2"
    };


    @Test
    void parse_withGoodData() throws Exception {
        String[] args = GraphParserTest.GRAPH_DEF;

        Graph g = GraphParser.parse(args);
        assertNotNull(g);
        assertEquals(4, g.getVertices().size());
        Graph.Vertex n1 = g.findVertexById("N1").orElseThrow();
        assertEquals(2, n1.getEdges().size());
        Graph.Vertex n2 = g.findVertexById("N2").orElseThrow();
        assertEquals(2, n2.getEdges().size());
        Graph.Vertex n3 = g.findVertexById("N3").orElseThrow();
        assertEquals(1, n3.getEdges().size());
        assertNotNull(n3.getEdges().stream().filter(e -> "N4".equals(e.toVertex().getId())).findFirst());
    }

    @Test
    void parseFromFile() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("testgraph.csv").getFile());
        InputStream inputStream = new FileInputStream(file);
        Graph p = GraphParser.parse(inputStream);

        assertNotNull(p);
    }

}