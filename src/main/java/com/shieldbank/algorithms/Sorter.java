package com.shieldbank.algorithms;

import com.shieldbank.model.Operation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Sorter {
    private Sorter() {
    }
    
    public static void bubbleSort(List<Operation> operations,
                                  Comparator<Operation> comparator) {
        int length = operations.size();
        boolean swapped = true;
        int pass = 0;
        
        while (swapped && pass < length - 1) {
            swapped = false;
            
            for (int index = 0; index < length - 1 - pass; index++) {
                
                if (comparator.compare(operations.get(index),
                        operations.get(index + 1)) > 0) {
                    swap(operations, index, index + 1);
                    swapped = true;
                }
            }
            pass++;
        }
    }

    public static void mergeSort(List<Operation> operations,
                                 Comparator<Operation> comparator) {
        
        if (operations.size() < 2) {
            return;
        }
        
        int middle = operations.size() / 2;
        List<Operation> left = new ArrayList<>(operations.subList(0, middle));
        List<Operation> right = new ArrayList<>(operations.subList(middle,
                operations.size()));
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        merge(operations, left, right, comparator);
    }

    private static void merge(List<Operation> target, List<Operation> left,
                              List<Operation> right,
                              Comparator<Operation> comparator) {
        int leftIndex = 0;
        int rightIndex = 0;
        int targetIndex = 0;
        
        while (leftIndex < left.size() && rightIndex < right.size()) {
            
            if (comparator.compare(left.get(leftIndex), right.get(rightIndex)) <= 0) {
                target.set(targetIndex, left.get(leftIndex));
                leftIndex++;
            } else {
                target.set(targetIndex, right.get(rightIndex));
                rightIndex++;
            }
            targetIndex++;
        }
        
        while (leftIndex < left.size()) {
            target.set(targetIndex, left.get(leftIndex));
            leftIndex++;
            targetIndex++;
        }
        
        while (rightIndex < right.size()) {
            target.set(targetIndex, right.get(rightIndex));
            rightIndex++;
            targetIndex++;
        }
    }

    public static long[] mergeSort(long[] values) {
        
        if (values.length < 2) {
            return values.clone();
        }
        
        long[] result = values.clone();
        long[] buffer = new long[values.length];
        mergeSortRange(result, buffer, 0, result.length - 1);
        return result;
    }

    private static void mergeSortRange(long[] target, long[] buffer,
                                       int from, int to) {
        
        if (from >= to) {
            return;
        }
        
        int middle = (from + to) >>> 1;
        mergeSortRange(target, buffer, from, middle);
        mergeSortRange(target, buffer, middle + 1, to);
        mergeRange(target, buffer, from, middle, to);
    }

    private static void mergeRange(long[] target, long[] buffer,
                                   int from, int middle, int to) {
        int leftIndex = from;
        int rightIndex = middle + 1;
        int bufferIndex = from;
        
        while (leftIndex <= middle && rightIndex <= to) {
            
            if (target[leftIndex] <= target[rightIndex]) {
                buffer[bufferIndex] = target[leftIndex];
                leftIndex++;
            } else {
                buffer[bufferIndex] = target[rightIndex];
                rightIndex++;
            }
            bufferIndex++;
        }
        
        while (leftIndex <= middle) {
            buffer[bufferIndex] = target[leftIndex];
            leftIndex++;
            bufferIndex++;
        }
        
        while (rightIndex <= to) {
            buffer[bufferIndex] = target[rightIndex];
            rightIndex++;
            bufferIndex++;
        }
        
        for (int index = from; index <= to; index++) {
            target[index] = buffer[index];
        }
    }

    private static void swap(List<Operation> operations,
                             int firstIndex, int secondIndex) {
        Operation temporary = operations.get(firstIndex);
        operations.set(firstIndex, operations.get(secondIndex));
        operations.set(secondIndex, temporary);
    }
}
