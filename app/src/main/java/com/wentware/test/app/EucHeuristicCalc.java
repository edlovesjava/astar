package com.wentware.test.app;

import java.util.function.Function;

public class EucHeuristicCalc {
    private String goal;
    private final Function<String, Integer> heuristic = fromVertex -> {
        int fromX = computeX(goal);
        int fromY = computeY(goal);
        int toX = computeX(fromVertex);
        int toY = computeY(fromVertex);

        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);

        double sqrt = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        return Math.toIntExact(Math.round(sqrt));
    };

    public EucHeuristicCalc(String goal) {
        this.goal = goal;
    }

    static Integer computeX(String id) {
        return Integer.parseInt(id.substring(1, 2));
    }

    static Integer computeY(String id) {
        return "ABCDE".indexOf(id.substring(0, 1));
    }

    public Function<String, Integer> getHeuristic() {
        return heuristic;
    }

    public String getGoal() {
        return goal;
    }
}