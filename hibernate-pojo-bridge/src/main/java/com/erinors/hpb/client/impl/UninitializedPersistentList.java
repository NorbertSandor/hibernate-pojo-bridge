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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Norbert Sándor
 */
public class UninitializedPersistentList<E> extends UninitializedPersistentCollection<E> implements List<E>,
        Serializable
{
    private static final long serialVersionUID = 1L;

    @Override
    public void add(int index, E element)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public E get(int index)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public int indexOf(Object o)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public int lastIndexOf(Object o)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public ListIterator<E> listIterator()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public ListIterator<E> listIterator(int index)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public E remove(int index)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public E set(int index, E element)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    // @Override - GWT doesn't emulate this method
    public List<E> subList(int fromIndex, int toIndex)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }
}
