package com.alex.miaomiao.config;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class EmptyMap<K, V> implements Map<K, V> {
    public int size() { return 0; }
    public boolean isEmpty() { return true; }
    public boolean containsKey(Object key) { return false; }
    public boolean containsValue(Object value) { return false; }
    public V get(Object key) { return null; }
    public V put(K key, V value) { return null; }
    public V remove(Object key) { return null; }
    public void putAll(Map<? extends K, ? extends V> m) { }
    public void clear() { }
    public Set<K> keySet() { throw new UnsupportedOperationException(); }
    public Collection<V> values() { throw new UnsupportedOperationException(); }
    public Set<Entry<K, V>> entrySet() { throw new UnsupportedOperationException(); }
}
