package com.arborsoft.workflow.util;

public class Pair<K, V> {
    private K key;
    private V value;

    public K key() { return this.key; }
    public V value() { return this.value; }

    private Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public static <K, V> Pair<K, V> pair(K key, V value) {
        return new Pair(key, value);
    }

    public boolean valid() {
        return this.key != null && this.value != null;
    }
}
