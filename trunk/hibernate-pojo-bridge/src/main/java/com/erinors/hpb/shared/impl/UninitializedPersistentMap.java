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

package com.erinors.hpb.shared.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Norbert Sándor
 */
public class UninitializedPersistentMap<K, V> implements Map<K, V>, Serializable
{
    private static final long serialVersionUID = 1L;

    protected static final String ERROR_MESSAGE = "Uninitialized collections should not be accessed.";

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
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
    public Set<java.util.Map.Entry<K, V>> entrySet()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public V get(Object key)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean isEmpty()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public Set<K> keySet()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public V put(K key, V value)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m)
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
    public Collection<V> values()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }
}
