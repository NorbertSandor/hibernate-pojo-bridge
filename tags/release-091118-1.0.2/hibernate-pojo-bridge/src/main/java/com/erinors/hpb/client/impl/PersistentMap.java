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
    public Set<java.util.Map.Entry<K, V>> entrySet()
    {
        // FIXME wrap the returned set
        return super.entrySet();
    }

    @Override
    public Set<K> keySet()
    {
        // FIXME wrap the returned set
        return super.keySet();
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
        // FIXME wrap the returned collection
        return super.values();
    }
}
