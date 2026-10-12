package com.shieldbank.algorithms;

import java.util.HashMap;
import java.util.Map;

public final class ContributionCalculator {
    private static final Map<Integer, Long> CACHE = new HashMap<>();

    private ContributionCalculator() {
    }

    public static long calculateContributionNaive(int month) {
        
        if (month < 1) {
            throw new IllegalArgumentException(
                    "Номер месяца должен быть не меньше 1");
        }
        
        if (month <= 2) {
            return 1L;
        }
        return calculateContributionNaive(month - 1)
                + calculateContributionNaive(month - 2);
    }

    public static long calculateContributionMemo(int month) {
        
        if (month < 1) {
            throw new IllegalArgumentException(
                    "Номер месяца должен быть не меньше 1");
        }
        
        if (month <= 2) {
            return 1L;
        }
        Long cached = CACHE.get(month);
        
        if (cached != null) {
            return cached;
        }
        long value = calculateContributionMemo(month - 1)
                + calculateContributionMemo(month - 2);
        CACHE.put(month, value);
        return value;
    }

    public static long calculateTotalContribution(int month) {
        long sum = 0L;
        
        for (int index = 1; index <= month; index++) {
            sum += calculateContributionMemo(index);
        }
        return sum;
    }
}
