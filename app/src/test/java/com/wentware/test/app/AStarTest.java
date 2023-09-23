package com.wentware.test.app;

import com.wentware.test.graph.Graph;
import com.wentware.test.graph.GraphParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class AStarTest {
    @Test
    void solveMazeGraphWithEucHeuristic() throws Exception {
        InputStream inputStream = getInputStreamFromFileName("testgraph.csv");
        Graph graph = GraphParser.parse(inputStream);

        AStar.Solution solution = testSolveWithGivenGraphAndHeuristic(graph, "B0", "B4", (String goal) -> new EucHeuristicCalc(goal).getHeuristic());

        assertNotNull(solution);
        assertEquals(14,solution.cost());
        assertEquals(15,solution.bestPath().size());
    }

    @Test
    void solveGraphWithTableHeuristic() throws Exception {
        InputStream graphInputStream = getInputStreamFromFileName("testgraph2.csv");
        Graph graph = GraphParser.parse(graphInputStream);
        InputStream heuristicTableInputStream = getInputStreamFromFileName("testgraph2map.csv");
        Map<String,Integer> heuristicTable = parseHeuristicTable(heuristicTableInputStream);

        AStar.Solution solution = testSolveWithGivenGraphAndHeuristic(graph, "V1", "V9", (n) -> new TableHeuristicCalc(heuristicTable).getHeuristic());

        assertNotNull(solution);
        assertEquals(5,solution.cost());
        assertEquals(4,solution.bestPath().size());
    }

    private AStar.Solution testSolveWithGivenGraphAndHeuristic(Graph graph, String sourceVertexName, String goalVertexName, Function<String, Function<String, Integer>> heuristicSup) {
        Graph.Vertex sourceVertex = graph.findVertexById(sourceVertexName).orElseThrow();
        Graph.Vertex goalVertex = graph.findVertexById(goalVertexName).orElseThrow();
        AStar aStar = new AStar(graph, heuristicSup.apply(goalVertex.getId()));

        return aStar.solve(sourceVertex, goalVertex);
    }

    private Map<String, Integer> parseHeuristicTable(InputStream inputStream) throws Exception {
        Scanner scanner = new Scanner(inputStream);
        Map<String,Integer> heuristicTable = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");


            if (parts.length == 2) {

                String from = parts[0];
                Integer value = Integer.valueOf(parts[1]);
                heuristicTable.put(from, value);
                //assume edge, add any new vertices
            } else {
                String message = String.format("could not parse line %s", line);
                throw new Exception(message);
            }
        }
        return heuristicTable;
    }

    private InputStream getInputStreamFromFileName(String heuristicTableFileName) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(heuristicTableFileName)).getFile());
        return new FileInputStream(file);
    }

}