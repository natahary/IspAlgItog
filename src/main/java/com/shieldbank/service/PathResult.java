package com.shieldbank.service;

import java.util.List;

/**
 * Результат поиска пути: суммарный вес и последовательность вершин.
 */
public class PathResult {

    private final long totalWeight;
    private final List<Long> path;

    /**
     * @param totalWeight суммарный вес пути
     * @param path        последовательность вершин
     */
    public PathResult(long totalWeight, List<Long> path) {
        this.totalWeight = totalWeight;
        this.path = path;
    }

    public long getTotalWeight() {
        return totalWeight;
    }

    public List<Long> getPath() {
        return path;
    }

    public boolean isReachable() {
        return !path.isEmpty();
    }
}
