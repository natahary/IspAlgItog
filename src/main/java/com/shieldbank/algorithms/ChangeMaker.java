package com.shieldbank.algorithms;

import java.util.ArrayList;
import java.util.List;

public final class ChangeMaker {
    private ChangeMaker() {
    }

    public static List<Integer> makeChangeGreedy(int[] denominations, int amount) {
        int[] sorted = sortAscending(denominations);
        List<Integer> result = new ArrayList<>();
        int rest = amount;
        
        for (int index = sorted.length - 1; index >= 0 && rest > 0; index--) {
            
            while (rest >= sorted[index]) {
                result.add(sorted[index]);
                rest -= sorted[index];
            }
        }
        
        if (rest != 0) {
            return null;
        }
        return result;
    }

    public static List<Integer> makeChangeOptimal(int[] denominations, int amount) {
        int[] bestCount = new int[amount + 1];
        int[] previousCoin = new int[amount + 1];
        
        for (int index = 1; index <= amount; index++) {
            bestCount[index] = Integer.MAX_VALUE;
        }
        
        bestCount[0] = 0;
        
        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            
            for (int denomination : denominations) {
                
                if (currentAmount >= denomination
                        && bestCount[currentAmount - denomination] != Integer.MAX_VALUE
                        && bestCount[currentAmount - denomination] + 1
                        < bestCount[currentAmount]) {
                    bestCount[currentAmount] =
                            bestCount[currentAmount - denomination] + 1;
                    previousCoin[currentAmount] = denomination;
                }
            }
        }
        
        if (bestCount[amount] == Integer.MAX_VALUE) {
            return null;
        }
        List<Integer> result = new ArrayList<>();
        int current = amount;
        
        while (current > 0) {
            result.add(previousCoin[current]);
            current -= previousCoin[current];
        }
        return result;
    }

    private static int[] sortAscending(int[] values) {
        int[] sorted = values.clone();
        boolean swapped = true;
        int pass = 0;
        
        while (swapped && pass < sorted.length - 1) {
            swapped = false;
            
            for (int index = 0; index < sorted.length - 1 - pass; index++) {
                
                if (sorted[index] > sorted[index + 1]) {
                    int temporary = sorted[index];
                    sorted[index] = sorted[index + 1];
                    sorted[index + 1] = temporary;
                    swapped = true;
                }
            }
            pass++;
        }
        return sorted;
    }
}
