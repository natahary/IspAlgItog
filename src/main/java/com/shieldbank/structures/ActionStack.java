package com.shieldbank.structures;

import com.shieldbank.model.Operation;

import java.util.ArrayList;
import java.util.List;

public class ActionStack {
    private final SinglyLinkedList<Operation> list = new SinglyLinkedList<>();

    public void push(Operation operation) {
        list.addFirst(operation);
    }

    public Operation pop() {
        return list.removeFirst();
    }

    public Operation peek() {
        return list.getFirst();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public List<Operation> toList() {
        List<Operation> result = new ArrayList<>(list.size());
        SinglyLinkedList<Operation> copy = new SinglyLinkedList<>();
        int remaining = list.size();
        
        for (int index = 0; index < remaining; index++) {
            Operation operation = list.removeFirst();
            result.add(operation);
            copy.addFirst(operation);
        }
        
        int copied = copy.size();
        
        for (int index = 0; index < copied; index++) {
            list.addFirst(copy.removeFirst());
        }
        return result;
    }
}
