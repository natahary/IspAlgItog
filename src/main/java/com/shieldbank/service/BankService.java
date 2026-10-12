package com.shieldbank.service;

import com.shieldbank.model.Account;
import com.shieldbank.model.Operation;
import com.shieldbank.structures.ActionStack;
import com.shieldbank.structures.BinarySearchTree;

public class BankService {
    private final BinarySearchTree registry = new BinarySearchTree();
    private final ActionStack journal = new ActionStack();

    public void register(Account account) {
        registry.insert(account);
    }

    public void transfer(long fromNumber, long toNumber, long amount) {
        Account source = requireAccount(fromNumber);
        Account target = requireAccount(toNumber);
        
        if (source.getBalance() < amount) {
            throw new IllegalArgumentException(
                    "Недостаточно средств на счёте " + fromNumber);
        }
        
        source.setBalance(source.getBalance() - amount);
        target.setBalance(target.getBalance() + amount);
        journal.push(new Operation(Operation.Type.TRANSFER, fromNumber, toNumber,
                amount, System.currentTimeMillis()));
    }

    public void block(long number) {
        Account account = requireAccount(number);
        long previousBalance = account.getBalance();
        account.setBalance(0L);
        journal.push(new Operation(Operation.Type.BLOCK, number,
                Operation.NO_RECIPIENT, previousBalance, System.currentTimeMillis()));
    }

    /**
     * Откатывает последнюю операцию и восстанавливает состояние счетов.
     *
     * @return откаченная операция
     * @throws IllegalStateException если журнал пуст
     */
    public Operation undoLast() {
        Operation operation = journal.pop();
        Account source = requireAccount(operation.getFromAccount());
        
        if (operation.getType() == Operation.Type.TRANSFER) {
            Account target = requireAccount(operation.getToAccount());
            source.setBalance(source.getBalance() + operation.getAmount());
            target.setBalance(target.getBalance() - operation.getAmount());
        } else {
            source.setBalance(operation.getAmount());
        }
        return operation;
    }

    public BinarySearchTree getRegistry() {
        return registry;
    }

    public ActionStack getJournal() {
        return journal;
    }

    private Account requireAccount(long number) {
        Account account = registry.find(number);
        
        if (account == null) {
            throw new IllegalArgumentException("Счёт " + number + " не найден");
        }
        return account;
    }
}
