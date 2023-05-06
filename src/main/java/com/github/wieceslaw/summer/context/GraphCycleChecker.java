package com.github.wieceslaw.summer.context;

import java.util.*;

public class GraphCycleChecker {
    private final Map<String, Set<String>> graph;
    private final Map<String, Boolean> used = new HashMap<>();
    private final Map<String, Boolean> inStack = new HashMap<>();
    private final List<String> order = new ArrayList<>();
    private boolean hasCycle = false;

    public GraphCycleChecker(Map<String, Set<String>> graph) {
        this.graph = graph;
        for (String v : graph.keySet()) {
            used.put(v, false);
        }
        check();
    }

    private void check() {
        for (String v : graph.keySet()) {
            if (!used.get(v)) {
                dfs(v, null);
            }
        }
    }

    private void dfs(String v, String p) {
        used.put(v, true);
        inStack.put(v, true);
        for (String u : graph.get(v)) {
            if (!v.equals(p) && used.get(u) && inStack.get(u)) {
                hasCycle = true;
            }
            if (!used.get(u)) {
                dfs(u, v);
            }
        }
        inStack.put(v, false);
        order.add(v);
    }

    public List<String> getOrder() {
        return order;
    }

    public boolean hasCycle() {
        return hasCycle;
    }
}
