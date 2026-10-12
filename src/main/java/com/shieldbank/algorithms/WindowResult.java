package com.shieldbank.algorithms;

/**
 * Результат поиска окна: стартовый индекс и сумма.
 */
public class WindowResult {

    private final int startIndex;
    private final long sum;

    /**
     * @param startIndex стартовый индекс окна
     * @param sum        сумма элементов окна
     */
    public WindowResult(int startIndex, long sum) {
        this.startIndex = startIndex;
        this.sum = sum;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public long getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return String.format("WindowResult{startIndex=%d, sum=%d}",
                startIndex, sum);
    }
}
