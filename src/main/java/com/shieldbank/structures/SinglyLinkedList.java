package com.shieldbank.structures;

/**
 * Собственный односвязный список.
 *
 * @param <T> тип элементов
 */
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

    /**
     * Добавляет значение в начало списка.
     *
     * @param value значение
     */
    public void addFirst(T value) {
        Node<T> node = new Node<>(value);
        node.next = head;
        head = node;
        size++;
    }

    /**
     * Удаляет и возвращает первый элемент.
     *
     * @return значение первого элемента
     * @throws IllegalStateException если список пуст
     */
    public T removeFirst() {
        if (head == null) {
            throw new IllegalStateException("Список пуст");
        }
        T value = head.value;
        head = head.next;
        size--;
        return value;
    }

    /**
     * Возвращает первый элемент без удаления.
     *
     * @return значение первого элемента
     * @throws IllegalStateException если список пуст
     */
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
