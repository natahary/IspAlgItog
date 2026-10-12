package com.shieldbank.algorithms;

import java.util.HashMap;
import java.util.Map;

/**
 * Накопительная программа: взнос в месяц n равен сумме двух предыдущих.
 */
public final class ContributionCalculator {

    private static final Map<Integer, Long> CACHE = new HashMap<>();

    private ContributionCalculator() {
    }

    /**
     * Наивная рекурсия.
     *
     * @param month номер месяца, начиная с 1
     * @return взнос
     * @throws IllegalArgumentException если номер месяца меньше 1
     */
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

    /**
     * Рекурсия с мемоизацией.
     *
     * @param month номер месяца, начиная с 1
     * @return взнос
     * @throws IllegalArgumentException если номер месяца меньше 1
     */
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

    /**
     * Накопленная сумма взносов за указанное число месяцев.
     *
     * @param month число месяцев
     * @return сумма взносов
     */
    public static long calculateTotalContribution(int month) {
        long sum = 0L;
        for (int index = 1; index <= month; index++) {
            sum += calculateContributionMemo(index);
        }
        return sum;
    }
}
