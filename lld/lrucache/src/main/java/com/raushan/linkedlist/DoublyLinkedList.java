package com.raushan.linkedlist;

public class DoublyLinkedList<K, V> {
    Node<K, V> head;
    Node<K, V> tail;

    public DoublyLinkedList() {
        head = new Node<>(null, null);
        tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public void addToFirst(Node<K, V> node) {
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    public void remove(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void moveToFront(Node<K, V> node) {
        remove(node);
        addToFirst(node);
    }

    public Node<K, V> removeLast() {
        // Check if list is empty (only dummies present)
        if (tail.prev == head) {
            return null;
        }

        Node<K, V> last = tail.prev;
        remove(last);
        return last;
    }
}
