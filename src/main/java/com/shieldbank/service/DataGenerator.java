package com.shieldbank.service;

import com.shieldbank.model.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Генератор тестовых данных с фиксированным seed.
 */
public final class DataGenerator {

    private static final long DEFAULT_SEED = 42L;
    private static final int ACCOUNT_RANGE = 1000;
    private static final int AMOUNT_RANGE = 1_000_000;
    private static final long START_TIMESTAMP = 1_700_000_000_000L;
    private static final long YEAR_IN_MILLISECONDS = 365L * 24 * 60 * 60 * 1000;

    private DataGenerator() {
    }

    /**
     * Генерирует список операций.
     *
     * @param count число операций
     * @return список операций
     */
    public static List<Operation> generateOperations(int count) {
        Random random = new Random(DEFAULT_SEED);
        List<Operation> operations = new ArrayList<>(count);
        for (int index = 0; index < count; index++) {
            long timestamp = START_TIMESTAMP
                    + (long) (random.nextDouble() * YEAR_IN_MILLISECONDS);
            operations.add(new Operation(
                    Operation.Type.TRANSFER,
                    random.nextInt(ACCOUNT_RANGE),
                    random.nextInt(ACCOUNT_RANGE),
                    random.nextInt(AMOUNT_RANGE),
                    timestamp));
        }
        return operations;
    }

    /**
     * Генерирует отсортированный список операций.
     *
     * @param count число операций
     * @return отсортированный список
     */
    public static List<Operation> generateSortedOperations(int count) {
        List<Operation> operations = generateOperations(count);
        operations.sort((first, second) -> Long.compare(first.getTimestamp(),
                second.getTimestamp()));
        return operations;
    }

    /**
     * Порядок элементов в почти отсортированном списке нарушается
     * перестановкой соседних пар.
     *
     * @param sortedOperations отсортированный список
     * @param swapsCount       число перестановок
     * @return почти отсортированный список
     */
    public static List<Operation> makeAlmostSorted(List<Operation> sortedOperations,
                                                   int swapsCount) {
        List<Operation> result = new ArrayList<>(sortedOperations);
        Random random = new Random(DEFAULT_SEED);
        for (int index = 0; index < swapsCount && result.size() > 1; index++) {
            int position = random.nextInt(result.size() - 1);
            Operation temporary = result.get(position);
            result.set(position, result.get(position + 1));
            result.set(position + 1, temporary);
        }
        return result;
    }

    /**
     * Генерирует массив случайных значений.
     *
     * @param count число значений
     * @return массив значений
     */
    public static long[] generateValues(int count) {
        Random random = new Random(DEFAULT_SEED);
        long[] values = new long[count];
        for (int index = 0; index < count; index++) {
            values[index] = random.nextInt(AMOUNT_RANGE);
        }
        return values;
    }
}
