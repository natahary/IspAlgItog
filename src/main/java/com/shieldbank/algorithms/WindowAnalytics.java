package com.shieldbank.algorithms;

import java.util.ArrayList;
import java.util.List;

public final class WindowAnalytics {
    private WindowAnalytics() {
    }

    public static long maxWindowSum(long[] values, int windowSize) {
        
        if (windowSize <= 0 || values.length < windowSize) {
            return -1L;
        }
        
        long sum = 0L;
        
        for (int index = 0; index < windowSize; index++) {
            sum += values[index];
        }
        
        long maximum = sum;
        
        for (int index = windowSize; index < values.length; index++) {
            sum += values[index] - values[index - windowSize];
            
            if (sum > maximum) {
                maximum = sum;
            }
        }
        return maximum;
    }

    public static List<WindowResult> findWindowsAboveLimit(long[] values,
                                                           int windowSize,
                                                           long limit) {
        List<WindowResult> result = new ArrayList<>();
        
        if (windowSize <= 0 || values.length < windowSize) {
            return result;
        }
        
        long sum = 0L;
        
        for (int index = 0; index < values.length; index++) {
            sum += values[index];
            
            if (index >= windowSize) {
                sum -= values[index - windowSize];
            }
            
            if (index >= windowSize - 1 && sum > limit) {
                result.add(new WindowResult(index - windowSize + 1, sum));
            }
        }
        return result;
    }

    public static long[] findPair(long[] sortedValues, long target) {
        int leftIndex = 0;
        int rightIndex = sortedValues.length - 1;
        long[] found = null;
        
        while (leftIndex < rightIndex && found == null) {
            long sum = sortedValues[leftIndex] + sortedValues[rightIndex];
            
            if (sum == target) {
                found = new long[]{sortedValues[leftIndex],
                        sortedValues[rightIndex]};
            } else if (sum < target) {
                leftIndex++;
            } else {
                rightIndex--;
            }
        }
        return found;
    }

    public static long[] findPairBruteForce(long[] values, long target) {
        long[] found = null;
        
        for (int firstIndex = 0; firstIndex < values.length && found == null;
             firstIndex++) {
            
            for (int secondIndex = firstIndex + 1;
                 secondIndex < values.length && found == null; secondIndex++) {
                
                if (values[firstIndex] + values[secondIndex] == target) {
                    found = new long[]{values[firstIndex], values[secondIndex]};
                }
            }
        }
        return found;
    }
}
