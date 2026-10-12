package com.shieldbank.structures;

import com.shieldbank.model.Account;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {
    private static final class Node {

        private final Account account;
        private Node left;
        private Node right;

        private Node(Account account) {
            this.account = account;
        }
    }

    private Node root;
    private int size;

    public void insert(Account account) {
        root = insertNode(root, account);
    }

    private Node insertNode(Node node, Account account) {
        
        if (node == null) {
            size++;
            return new Node(account);
        }
        
        int comparison = Long.compare(account.getNumber(), node.account.getNumber());
        
        if (comparison < 0) {
            node.left = insertNode(node.left, account);
        } else if (comparison > 0) {
            node.right = insertNode(node.right, account);
        } else {
            throw new IllegalArgumentException(
                    "Счёт с номером " + account.getNumber() + " уже существует");
        }
        return node;
    }

    public Account find(long number) {
        Node current = root;
        Account found = null;
        
        while (current != null && found == null) {
            int comparison = Long.compare(number, current.account.getNumber());
            
            if (comparison == 0) {
                found = current.account;
            } else if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return found;
    }

    public List<Account> collectInOrder() {
        List<Account> result = new ArrayList<>();
        collectInOrder(root, result);
        return result;
    }

    private void collectInOrder(Node node, List<Account> result) {
        
        if (node == null) {
            return;
        }
        collectInOrder(node.left, result);
        result.add(node.account);
        collectInOrder(node.right, result);
    }

    public int size() {
        return size;
    }
}
