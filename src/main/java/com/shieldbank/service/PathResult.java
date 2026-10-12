package com.shieldbank.service;

import java.util.List;

public class PathResult {
    private final long totalWeight;
    private final List<Long> path;

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
