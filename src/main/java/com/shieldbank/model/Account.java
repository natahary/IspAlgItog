package com.shieldbank.model;

/**
 * Банковский счёт с уникальным номером и балансом в копейках.
 */
public class Account {

    private final long number;
    private long balance;

    /**
     * @param number  уникальный номер счёта
     * @param balance начальный баланс в копейках
     */
    public Account(long number, long balance) {
        this.number = number;
        this.balance = balance;
    }

    public long getNumber() {
        return number;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("Account{number=%d, balance=%d}", number, balance);
    }
}
