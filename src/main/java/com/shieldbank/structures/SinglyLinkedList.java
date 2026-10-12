package com.shieldbank.structures;

public class SinglyLinkedList<T> {
    private static final class Node<T> {

        private final T value;
        private Node<T> next;

        private Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private int size;

    public void addFirst(T value) {
        Node<T> node = new Node<>(value);
        node.next = head;
        head = node;
        size++;
    }

    public T removeFirst() {
        
        if (head == null) {
            throw new IllegalStateException("Список пуст");
        }
        
        T value = head.value;
        head = head.next;
        size--;
        return value;
    }

    public T getFirst() {
        
        if (head == null) {
            throw new IllegalStateException("Список пуст");
        }
        return head.value;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}
