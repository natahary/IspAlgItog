package com.shieldbank.algorithms;

import com.shieldbank.model.Operation;

import java.util.ArrayList;
import java.util.List;

/**
 * Собственный бинарный поиск по отсортированному списку операций.
 */
public final class Searcher {

    private Searcher() {
    }

    /**
     * Возвращает индекс первого элемента с timestamp, не меньшим ключа.
     *
     * @param sortedOperations список, отсортированный по timestamp
     * @param key              пороговое значение
     * @return индекс
     */
    public static int lowerBound(List<Operation> sortedOperations, long key) {
        int low = 0;
        int high = sortedOperations.size();
        while (low < high) {
            int middle = (low + high) >>> 1;
            if (sortedOperations.get(middle).getTimestamp() < key) {
                low = middle + 1;
            } else {
                high = middle;
            }
        }
        return low;
    }

    /**
     * Возвращает индекс первого элемента с timestamp, большим ключа.
     *
     * @param sortedOperations список, отсортированный по timestamp
     * @param key              пороговое значение
     * @return индекс
     */
    public static int upperBound(List<Operation> sortedOperations, long key) {
        int low = 0;
        int high = sortedOperations.size();
        while (low < high) {
            int middle = (low + high) >>> 1;
            if (sortedOperations.get(middle).getTimestamp() <= key) {
                low = middle + 1;
            } else {
                high = middle;
            }
        }
        return low;
    }

    /**
     * Возвращает операции за интервал времени включительно.
     *
     * @param sortedOperations список, отсортированный по timestamp
     * @param fromInclusive    нижняя граница
     * @param toInclusive      верхняя граница
     * @return список операций за интервал
     */
    public static List<Operation> findBetween(List<Operation> sortedOperations,
                                              long fromInclusive,
                                              long toInclusive) {
        int left = lowerBound(sortedOperations, fromInclusive);
        int right = upperBound(sortedOperations, toInclusive);
        if (left >= right) {
            return new ArrayList<>();
        }
        return new ArrayList<>(sortedOperations.subList(left, right));
    }
}
