package com.shieldbank.model;


public class Account {
    private final long number;
    private long balance;

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
