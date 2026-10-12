package com.shieldbank.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Собственный ориентированный взвешенный граф переводов.
 */
public class Graph {

    /**
     * Ребро графа.
     */
    public static final class Edge {

        private final long to;
        private final int weight;

        /**
         * @param to     конечная вершина
         * @param weight вес ребра
         */
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

    /**
     * Добавляет вершину, если её ещё нет.
     *
     * @param vertex идентификатор вершины
     */
    public void addVertex(long vertex) {
        adjacency.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * Добавляет направленное ребро.
     *
     * @param from   начальная вершина
     * @param to     конечная вершина
     * @param weight вес ребра
     */
    public void addEdge(long from, long to, int weight) {
        addVertex(from);
        addVertex(to);
        adjacency.get(from).add(new Edge(to, weight));
    }

    /**
     * Возвращает исходящие рёбра вершины.
     *
     * @param vertex вершина
     * @return список рёбер
     */
    public List<Edge> getNeighbors(long vertex) {
        return adjacency.getOrDefault(vertex, Collections.emptyList());
    }

    /**
     * Возвращает копию множества вершин.
     *
     * @return множество вершин
     */
    public Set<Long> getVertices() {
        return new HashSet<>(adjacency.keySet());
    }
}
