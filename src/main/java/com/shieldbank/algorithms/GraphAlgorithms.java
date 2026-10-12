package com.shieldbank.algorithms;

import com.shieldbank.service.PathResult;
import com.shieldbank.structures.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public final class GraphAlgorithms {
    private static final int COLOR_WHITE = 0;
    private static final int COLOR_GRAY = 1;
    private static final int COLOR_BLACK = 2;

    private GraphAlgorithms() {
    }
    
    public static List<Long> breadthFirstSearch(Graph graph, long start) {
        List<Long> order = new ArrayList<>();
        Set<Long> visited = new HashSet<>();
        Deque<Long> queue = new ArrayDeque<>();
        queue.add(start);
        visited.add(start);
        
        while (!queue.isEmpty()) {
            long current = queue.poll();
            order.add(current);
            
            for (Graph.Edge edge : graph.getNeighbors(current)) {
                
                if (visited.add(edge.getTo())) {
                    queue.add(edge.getTo());
                }
            }
        }
        return order;
    }

    public static List<Long> findCycle(Graph graph) {
        Map<Long, Integer> color = new HashMap<>();
        Map<Long, Long> parent = new HashMap<>();
        
        for (long vertex : graph.getVertices()) {
            color.put(vertex, COLOR_WHITE);
        }
        
        for (long vertex : graph.getVertices()) {
            List<Long> cycle = findCycleFrom(graph, vertex, color, parent);
            
            if (!cycle.isEmpty()) {
                return cycle;
            }
        }
        return Collections.emptyList();
    }

    private static List<Long> findCycleFrom(Graph graph, long vertex,
                                            Map<Long, Integer> color,
                                            Map<Long, Long> parent) {
        color.put(vertex, COLOR_GRAY);
        
        for (Graph.Edge edge : graph.getNeighbors(vertex)) {
            long neighbor = edge.getTo();
            int neighborColor = color.getOrDefault(neighbor, COLOR_WHITE);
            
            if (neighborColor == COLOR_GRAY) {
                return buildCycle(vertex, neighbor, parent);
            }
            
            if (neighborColor == COLOR_WHITE) {
                parent.put(neighbor, vertex);
                List<Long> cycle = findCycleFrom(graph, neighbor, color, parent);
                
                if (!cycle.isEmpty()) {
                    return cycle;
                }
            }
        }
        color.put(vertex, COLOR_BLACK);
        return Collections.emptyList();
    }

    private static List<Long> buildCycle(long from, long to,
                                         Map<Long, Long> parent) {
        LinkedList<Long> path = new LinkedList<>();
        path.addFirst(from);
        long current = from;
        
        while (current != to) {
            current = parent.get(current);
            path.addFirst(current);
        }
        path.addLast(to);
        return new ArrayList<>(path);
    }

    public static int countComponents(Graph graph) {
        Map<Long, Set<Long>> undirected = new HashMap<>();
        
        for (long vertex : graph.getVertices()) {
            undirected.putIfAbsent(vertex, new HashSet<>());
        }
        
        for (long vertex : graph.getVertices()) {
            
            for (Graph.Edge edge : graph.getNeighbors(vertex)) {
                undirected.get(vertex).add(edge.getTo());
                undirected.computeIfAbsent(edge.getTo(), key -> new HashSet<>())
                        .add(vertex);
            }
        }
        Set<Long> visited = new HashSet<>();
        int componentCount = 0;
        
        for (long vertex : undirected.keySet()) {
            
            if (visited.add(vertex)) {
                componentCount++;
                Deque<Long> stack = new ArrayDeque<>();
                stack.push(vertex);
                
                while (!stack.isEmpty()) {
                    long current = stack.pop();
                    
                    for (long neighbor : undirected.get(current)) {
                        
                        if (visited.add(neighbor)) {
                            stack.push(neighbor);
                        }
                    }
                }
            }
        }
        return componentCount;
    }

    public static Map<Long, PathResult> findShortestPaths(Graph graph, long start) {
        Map<Long, Integer> distance = new HashMap<>();
        Map<Long, Long> previous = new HashMap<>();
        
        for (long vertex : graph.getVertices()) {
            distance.put(vertex, Integer.MAX_VALUE);
        }
        
        distance.put(start, 0);
        PriorityQueue<long[]> queue = new PriorityQueue<>(
                Comparator.comparingInt(entry -> (int) entry[1]));
        queue.add(new long[]{start, 0L});
        
        while (!queue.isEmpty()) {
            long[] current = queue.poll();
            long vertex = current[0];
            int currentDistance = (int) current[1];
            
            if (currentDistance > distance.get(vertex)) {
                continue;
            }
            
            for (Graph.Edge edge : graph.getNeighbors(vertex)) {
                int candidate = currentDistance + edge.getWeight();
                
                if (candidate < distance.get(edge.getTo())) {
                    distance.put(edge.getTo(), candidate);
                    previous.put(edge.getTo(), vertex);
                    queue.add(new long[]{edge.getTo(), candidate});
                }
            }
        }
        Map<Long, PathResult> result = new HashMap<>();
        
        for (long vertex : graph.getVertices()) {
            int total = distance.get(vertex);
            
            if (total == Integer.MAX_VALUE) {
                result.put(vertex, new PathResult(-1L, Collections.emptyList()));
            } else {
                List<Long> path = restorePath(vertex, start, previous);
                result.put(vertex, new PathResult(total, path));
            }
        }
        return result;
    }

    private static List<Long> restorePath(long vertex, long start,
                                          Map<Long, Long> previous) {
        LinkedList<Long> path = new LinkedList<>();
        long current = vertex;
        path.addFirst(current);
        
        while (current != start) {
            current = previous.get(current);
            path.addFirst(current);
        }
        return new ArrayList<>(path);
    }
}
