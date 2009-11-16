package com.erinors.hpb.client.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class UninitializedPersistentSortedMap<K, V> extends TreeMap<K, V>
{
    private static final long serialVersionUID = 1L;

    protected static final String ERROR_MESSAGE = "Uninitialized collections should not be accessed.";
    
    public UninitializedPersistentSortedMap()
    {
    }

    public UninitializedPersistentSortedMap(Map<? extends K, ? extends V> m)
    {
        super(m);
    }

    public UninitializedPersistentSortedMap(SortedMap<K, ? extends V> m)
    {
        super(m);
    }

    public UninitializedPersistentSortedMap(Comparator<? super Object> comparator)
    {
        super(comparator);
    }

    @Override
    public java.util.Map.Entry<K, V> ceilingEntry(K key)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public K ceilingKey(K key)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public Object clone()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public Comparator<? super K> comparator()
    {
        return super.comparator();
    }

    @Override
    public boolean containsKey(Object key)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean containsValue(Object value)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public NavigableSet<K> descendingKeySet()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public NavigableMap<K, V> descendingMap()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public java.util.Map.Entry<K, V> firstEntry()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public K firstKey()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public java.util.Map.Entry<K, V> floorEntry(K key)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public K floorKey(K key)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public V get(Object key)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public SortedMap<K, V> headMap(K toKey)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public java.util.Map.Entry<K, V> higherEntry(K key)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public K higherKey(K key)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public Set<K> keySet()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public java.util.Map.Entry<K, V> lastEntry()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public K lastKey()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public java.util.Map.Entry<K, V> lowerEntry(K key)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public K lowerKey(K key)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public NavigableSet<K> navigableKeySet()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public java.util.Map.Entry<K, V> pollFirstEntry()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public java.util.Map.Entry<K, V> pollLastEntry()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public V put(K key, V value)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public V remove(Object key)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public int size()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public Collection<V> values()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean equals(Object o)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public int hashCode()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean isEmpty()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public String toString()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }
}
