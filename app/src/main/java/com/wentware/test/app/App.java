/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.wentware.test.app;

import com.wentware.test.graph.Graph;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class App {
    public static void main(String[] args) {
        App app = new App();

        int res = app.run(args);
        System.exit(res);
    }

    /*
     0  1  2  3  4
    +--+--+--+--+--+
A   |     |  |     |
    +  +  +  +  +  +
B =>   |  |  |  |   =>
    +  +--+  +  +--+
C   |        |     |
    +--+  +  +--+  +
D   |  |  |  |  |  |
    +  +  +  +  +  +
E   |     |        |
    +--+--+--+--+--+
     */
    private static Graph buildSimpleMazeGraph() {
        Graph myGraph = new Graph();

        //col0
        Graph.Vertex a0 = myGraph.addVertex("A0");
        Graph.Vertex b0 = myGraph.addVertex("B0");
        Graph.Vertex c0 = myGraph.addVertex("C0");
        Graph.Vertex d0 = myGraph.addVertex("D0");
        Graph.Vertex e0 = myGraph.addVertex("E0");

        Graph.Vertex a1 = myGraph.addVertex("A1");
        Graph.Vertex b1 = myGraph.addVertex("B1");
        Graph.Vertex c1 = myGraph.addVertex("C1");
        Graph.Vertex d1 = myGraph.addVertex("D1");
        Graph.Vertex e1 = myGraph.addVertex("E1");

        Graph.Vertex a2 = myGraph.addVertex("A2");
        Graph.Vertex b2 = myGraph.addVertex("B2");
        Graph.Vertex c2 = myGraph.addVertex("C2");
        Graph.Vertex d2 = myGraph.addVertex("D2");
        Graph.Vertex e2 = myGraph.addVertex("E2");

        Graph.Vertex a3 = myGraph.addVertex("A3");
        Graph.Vertex b3 = myGraph.addVertex("B3");
        Graph.Vertex c3 = myGraph.addVertex("C3");
        Graph.Vertex d3 = myGraph.addVertex("D3");
        Graph.Vertex e3 = myGraph.addVertex("E3");

        Graph.Vertex a4 = myGraph.addVertex("A4");
        Graph.Vertex b4 = myGraph.addVertex("B4");
        Graph.Vertex c4 = myGraph.addVertex("C4");
        Graph.Vertex d4 = myGraph.addVertex("D4");
        Graph.Vertex e4 = myGraph.addVertex("E4");

        a0.addEdgeTo(a1, 1);
        b0.addEdgeTo(a0, 1);
        b0.addEdgeTo(c0, 1);
        c0.addEdgeTo(c1, 1);
        e0.addEdgeTo(d0, 1);

        a1.addEdgeTo(b1, 1);
        c1.addEdgeTo(c2, 1);
        c1.addEdgeTo(d1, 1);
        d1.addEdgeTo(e1, 1);
        e1.addEdgeTo(e0, 1);

        b2.addEdgeTo(a2, 1);
        c2.addEdgeTo(b2, 1);
        c2.addEdgeTo(d2, 1);
        d2.addEdgeTo(e2, 1);
        e2.addEdgeTo(e3, 1);

        a3.addEdgeTo(a4, 1);
        b3.addEdgeTo(a3, 1);
        c3.addEdgeTo(b3, 1);
        e3.addEdgeTo(d3, 1);
        e3.addEdgeTo(e4, 1);

        a4.addEdgeTo(b4, 1);
        c4.addEdgeTo(c3, 1);
        d4.addEdgeTo(c4, 1);
        e4.addEdgeTo(d4, 1);

        return myGraph;
    }

    private int run(String[] args) {
        //todo replace with filename and parse
        Graph g = buildSimpleMazeGraph();
        Graph.Vertex source = g.findVertexById("B0").orElseThrow();
        Graph.Vertex goal = g.findVertexById("B4").orElseThrow();
        EucHeuristicCalc eucHeuristicCalc = new EucHeuristicCalc(goal.getId());
        AStar a = new AStar(g, eucHeuristicCalc.getHeuristic());

        AStar.Solution solution = a.solve(source, goal);
        log.info("cost={},path={}", solution.cost(), solution.bestPath());
        return 0; //ok
    }

}
