package com.wentware.test.graph;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

public final class GraphParser {
    private GraphParser() {
        //static members only
    }

    public static Graph parse(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(String.join("\n", args))) {
            return buildGraphGivenScanner(scanner);
        }
    }

    public static Graph parse(String fileName) throws Exception {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            return buildGraphGivenScanner(scanner);
        }
    }

    public static Graph parse(InputStream inputStream) throws Exception {
        try (Scanner scanner = new Scanner(inputStream)) {
            return buildGraphGivenScanner(scanner);
        }
    }



    private static Graph buildGraphGivenScanner(Scanner scanner) throws Exception {
        Graph graph = new Graph();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");


            if (parts.length == 3) {

                Graph.Vertex from = graph.addVertex(parts[0]);
                Graph.Vertex to = graph.addVertex(parts[1]);
                int weight = Integer.parseInt(parts[2]);
                from.addEdgeTo(to, weight);
                //assume edge, add any new vertices
            } else {
                String message = String.format("could not parse line %s", line);
                throw new Exception(message);
            }
        }
        return graph;
    }
}
