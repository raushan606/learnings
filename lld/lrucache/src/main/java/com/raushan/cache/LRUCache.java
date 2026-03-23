package com.raushan.cache;

import com.raushan.linkedlist.DoublyLinkedList;
import com.raushan.linkedlist.Node;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> implements Cache<K, V> {
    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private final DoublyLinkedList<K, V> list;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.list = new DoublyLinkedList<>();
    }

    public synchronized V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        Node<K, V> node = map.get(key);
        list.moveToFront(node);
        return node.getValue();
    }

    public synchronized void put(K key, V value) {
        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            list.moveToFront(node);
            return;
        } else {
            if (map.size() == capacity) {
                Node<K, V> last = list.removeLast();
                map.remove(last.getKey());
            }
            Node<K, V> newNode = new Node<>(key, value);
            map.put(key, newNode);
            list.addToFirst(newNode);
        }
    }


}
