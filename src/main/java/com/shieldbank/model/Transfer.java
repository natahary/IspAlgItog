package com.shieldbank.model;

/**
 * Направленный перевод между счетами — ребро графа переводов.
 */
public class Transfer {

    private final long from;
    private final long to;
    private final int commission;

    /**
     * @param from       счёт-источник
     * @param to         счёт-получатель
     * @param commission комиссия
     */
    public Transfer(long from, long to, int commission) {
        this.from = from;
        this.to = to;
        this.commission = commission;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public int getCommission() {
        return commission;
    }

    @Override
    public String toString() {
        return String.format("Transfer{from=%d, to=%d, commission=%d}",
                from, to, commission);
    }
}
