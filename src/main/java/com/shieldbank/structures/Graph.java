package com.shieldbank.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    public static final class Edge {

        private final long to;
        private final int weight;

        public Edge(long to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        public long getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }
    }

    private final Map<Long, List<Edge>> adjacency = new HashMap<>();

    public void addVertex(long vertex) {
        adjacency.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(long from, long to, int weight) {
        addVertex(from);
        addVertex(to);
        adjacency.get(from).add(new Edge(to, weight));
    }

    public List<Edge> getNeighbors(long vertex) {
        return adjacency.getOrDefault(vertex, Collections.emptyList());
    }

    public Set<Long> getVertices() {
        return new HashSet<>(adjacency.keySet());
    }
}
