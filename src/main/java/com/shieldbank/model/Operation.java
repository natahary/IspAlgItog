package com.shieldbank.model;

/**
 * Операция по счёту: перевод, блокировка, разблокировка.
 */
public class Operation {

    /**
     * Тип операции.
     */
    public enum Type {
        TRANSFER,
        BLOCK,
        UNBLOCK
    }

    /**
     * Значение, обозначающее отсутствие счёта-получателя.
     */
    public static final long NO_RECIPIENT = -1L;

    private final Type type;
    private final long fromAccount;
    private final long toAccount;
    private final long amount;
    private final long timestamp;

    /**
     * @param type        тип операции
     * @param fromAccount счёт-источник
     * @param toAccount   счёт-получатель или NO_RECIPIENT
     * @param amount      сумма в копейках
     * @param timestamp   время операции в миллисекундах
     */
    public Operation(Type type, long fromAccount, long toAccount,
                     long amount, long timestamp) {
        this.type = type;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Type getType() {
        return type;
    }

    public long getFromAccount() {
        return fromAccount;
    }

    public long getToAccount() {
        return toAccount;
    }

    public long getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format(
                "Operation{type=%s, from=%d, to=%d, amount=%d, timestamp=%d}",
                type, fromAccount, toAccount, amount, timestamp);
    }
}
