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

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Norbert Sándor
 */
public class UninitializedPersistentSortedSet<E> extends TreeSet<E>
{
    private static final long serialVersionUID = 1L;

    private static final String ERROR_MESSAGE = "Uninitialized collections should not be accessed.";

    public UninitializedPersistentSortedSet()
    {
    }

    public UninitializedPersistentSortedSet(Collection<? extends E> c)
    {
        super(c);
    }

    public UninitializedPersistentSortedSet(Comparator<? super E> comparator)
    {
        super(comparator);
    }

    public UninitializedPersistentSortedSet(SortedSet<E> s)
    {
        super(s);
    }

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

    // @Override
    public E ceiling(E e)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    // @Override
    public Object clone()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public Comparator<? super E> comparator()
    {
        return super.comparator();
    }

    @Override
    public boolean contains(Object o)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    // @Override
    public Iterator<E> descendingIterator()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    // @Override
    // public NavigableSet<E> descendingSet()
    // {
    // throw new UnsupportedOperationException(ERROR_MESSAGE);
    // }

    @Override
    public E first()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    // @Override
    public E floor(E e)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    // @Override
    // public NavigableSet<E> headSet(E toElement, boolean inclusive)
    // {
    // throw new UnsupportedOperationException(ERROR_MESSAGE);
    // }

    @Override
    public SortedSet<E> headSet(E toElement)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    // @Override
    public E higher(E e)
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
    public E last()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    // @Override
    public E lower(E e)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    // @Override
    public E pollFirst()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    // @Override
    public E pollLast()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean remove(Object o)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public int size()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    // @Override
    // public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
    // throw new UnsupportedOperationException(ERROR_MESSAGE);
    // }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    // @Override
    // public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
    // throw new UnsupportedOperationException(ERROR_MESSAGE);
    // }

    @Override
    public SortedSet<E> tailSet(E fromElement)
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
    public boolean removeAll(Collection<?> c)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Override
    public boolean retainAll(Collection<?> c)
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

    @Override
    public String toString()
    {
        throw new UnsupportedOperationException(ERROR_MESSAGE);
    }
}
