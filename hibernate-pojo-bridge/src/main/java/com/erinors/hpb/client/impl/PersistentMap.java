/*
 * Copyright 2009 Norbert Sándor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.erinors.hpb.client.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Norbert Sándor
 */
public class PersistentMap<K, V> extends HashMap<K, V>
{
    private static final long serialVersionUID = 1L;

    private boolean dirty;

    public PersistentMap()
    {
    }

    public PersistentMap(int initialCapacity)
    {
        super(initialCapacity);
    }

    public boolean isDirty()
    {
        return dirty;
    }

    public void setDirty(boolean dirty)
    {
        this.dirty = dirty;
    }

    private boolean markAsDirty(boolean value)
    {
        if (value)
        {
            setDirty(true);
        }

        return value;
    }

    @Override
    public void clear()
    {
        boolean empty = isEmpty();
        super.clear();
        markAsDirty(!empty);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        final Set<Map.Entry<K, V>> entries = super.entrySet();

        return new Set<Map.Entry<K, V>>()
        {
            @Override
            public boolean add(java.util.Map.Entry<K, V> e)
            {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends java.util.Map.Entry<K, V>> c)
            {
                throw new UnsupportedOperationException();
            }

            @Override
            public void clear()
            {
                boolean empty = isEmpty();
                markAsDirty(!empty);
            }

            @Override
            public boolean contains(Object o)
            {
                return entries.contains(o);
            }

            @Override
            public boolean containsAll(Collection<?> c)
            {
                return entries.containsAll(c);
            }

            @Override
            public boolean isEmpty()
            {
                return entries.isEmpty();
            }

            @Override
            public Iterator<Map.Entry<K, V>> iterator()
            {
                final Iterator<Map.Entry<K, V>> it = entries.iterator();

                return new Iterator<Map.Entry<K, V>>()
                {

                    @Override
                    public boolean hasNext()
                    {
                        return it.hasNext();
                    }

                    @Override
                    public java.util.Map.Entry<K, V> next()
                    {
                        return it.next();
                    }

                    @Override
                    public void remove()
                    {
                        it.remove();
                        markAsDirty(true);
                    }
                };
            }

            @Override
            public boolean remove(Object o)
            {
                boolean removed = entries.remove(o);
                markAsDirty(removed);
                return removed;
            }

            @Override
            public boolean removeAll(Collection<?> c)
            {
                boolean changed = entries.removeAll(c);
                markAsDirty(changed);
                return changed;
            }

            @Override
            public boolean retainAll(Collection<?> c)
            {
                boolean changed = entries.retainAll(c);
                markAsDirty(changed);
                return changed;
            }

            @Override
            public int size()
            {
                return entries.size();
            }

            @Override
            public Object[] toArray()
            {
                return entries.toArray();
            }

            @Override
            public <T> T[] toArray(T[] a)
            {
                return entries.toArray(a);
            }
        };
    }

    @Override
    public Set<K> keySet()
    {
        final Set<K> keys = super.keySet();

        return new Set<K>()
        {
            @Override
            public boolean add(K e)
            {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends K> c)
            {
                throw new UnsupportedOperationException();
            }

            @Override
            public void clear()
            {
                boolean empty = isEmpty();
                markAsDirty(!empty);
            }

            @Override
            public boolean contains(Object o)
            {
                return keys.contains(o);
            }

            @Override
            public boolean containsAll(Collection<?> c)
            {
                return keys.containsAll(c);
            }

            @Override
            public boolean isEmpty()
            {
                return keys.isEmpty();
            }

            @Override
            public Iterator<K> iterator()
            {
                final Iterator<K> it = keys.iterator();

                return new Iterator<K>()
                {
                    @Override
                    public boolean hasNext()
                    {
                        return it.hasNext();
                    }

                    @Override
                    public K next()
                    {
                        return it.next();
                    }

                    @Override
                    public void remove()
                    {
                        it.remove();
                        markAsDirty(true);
                    }
                };
            }

            @Override
            public boolean remove(Object o)
            {
                boolean removed = keys.remove(o);
                markAsDirty(removed);
                return removed;
            }

            @Override
            public boolean removeAll(Collection<?> c)
            {
                boolean removed = keys.removeAll(c);
                markAsDirty(removed);
                return removed;
            }

            @Override
            public boolean retainAll(Collection<?> c)
            {
                boolean removed = keys.retainAll(c);
                markAsDirty(removed);
                return removed;
            }

            @Override
            public int size()
            {
                return keys.size();
            }

            @Override
            public Object[] toArray()
            {
                return keys.toArray();
            }

            @Override
            public <T> T[] toArray(T[] a)
            {
                return keys.toArray(a);
            }
        };
    }

    @Override
    public V put(K key, V value)
    {
        V old = super.put(key, value);
        markAsDirty(old != value);
        return old;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        super.putAll(m);
        markAsDirty(true);
    }

    @Override
    public V remove(Object key)
    {
        V v = super.remove(key);
        markAsDirty(v != null);
        return v;
    }

    @Override
    public Collection<V> values()
    {
        final Collection<V> values = super.values();

        return new Collection<V>()
        {
            @Override
            public boolean add(V e)
            {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends V> c)
            {
                throw new UnsupportedOperationException();
            }

            @Override
            public void clear()
            {
                boolean empty = isEmpty();
                values.clear();
                markAsDirty(!empty);
            }

            @Override
            public boolean contains(Object o)
            {
                return values.contains(o);
            }

            @Override
            public boolean containsAll(Collection<?> c)
            {
                return values.containsAll(c);
            }

            @Override
            public boolean isEmpty()
            {
                return values.isEmpty();
            }

            @Override
            public Iterator<V> iterator()
            {
                final Iterator<V> it = values.iterator();

                return new Iterator<V>()
                {
                    @Override
                    public boolean hasNext()
                    {
                        return it.hasNext();
                    }

                    @Override
                    public V next()
                    {
                        return it.next();
                    }

                    @Override
                    public void remove()
                    {
                        it.remove();
                        markAsDirty(true);
                    }
                };
            }

            @Override
            public boolean remove(Object o)
            {
                boolean r = values.remove(o);
                markAsDirty(r);
                return r;
            }

            @Override
            public boolean removeAll(Collection<?> c)
            {
                boolean r = values.removeAll(c);
                markAsDirty(r);
                return r;
            }

            @Override
            public boolean retainAll(Collection<?> c)
            {
                boolean r = values.retainAll(c);
                markAsDirty(r);
                return r;
            }

            @Override
            public int size()
            {
                return values.size();
            }

            @Override
            public Object[] toArray()
            {
                return values.toArray();
            }

            @Override
            public <T> T[] toArray(T[] a)
            {
                return values.toArray(a);
            }
        };
    }
}
