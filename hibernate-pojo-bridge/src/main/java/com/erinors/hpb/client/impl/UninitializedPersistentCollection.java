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
import java.util.Iterator;

/**
 * @author Norbert Sándor
 */
public abstract class UninitializedPersistentCollection<E> implements Collection<E>
{
    protected static final String ERROR_MESSAGE = "Uninitialized collections should not be accessed.";

    @Override
    public boolean add(E e)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean addAll(Collection<? extends E> c)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean contains(Object o)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean isEmpty()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public Iterator<E> iterator()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean remove(Object o)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public int size()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public Object[] toArray()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }
}
