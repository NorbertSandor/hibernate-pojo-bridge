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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Norbert Sándor
 */
public class PersistentList<E> extends ArrayList<E>
{
    private static final long serialVersionUID = 1L;

    private boolean dirty;

    public PersistentList()
    {
    }

    public PersistentList(int initialCapacity)
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
    public boolean add(E e)
    {
        return markAsDirty(super.add(e));
    }

    @Override
    public void add(int index, E element)
    {
        super.add(index, element);
        markAsDirty(true);
    }

    @Override
    public boolean addAll(Collection<? extends E> c)
    {
        return markAsDirty(super.addAll(c));
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c)
    {
        return markAsDirty(super.addAll(index, c));
    }

    @Override
    public void clear()
    {
        boolean empty = isEmpty();
        super.clear();
        markAsDirty(!empty);
    }

    @Override
    public E remove(int index)
    {
        E removed = super.remove(index);
        markAsDirty(true);
        return removed;
    }

    @Override
    public boolean remove(Object o)
    {
        return markAsDirty(super.remove(o));
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex)
    {
        super.removeRange(fromIndex, toIndex);
        markAsDirty(true);
    }

    @Override
    public E set(int index, E element)
    {
        E old = super.set(index, element);
        markAsDirty(old != element);
        return old;
    }

    @Override
    public Iterator<E> iterator()
    {
        final Iterator<E> it = super.iterator();
        return new Iterator<E>()
        {
            @Override
            public boolean hasNext()
            {
                return it.hasNext();
            }

            @Override
            public E next()
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
    public ListIterator<E> listIterator()
    {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index)
    {
        final ListIterator<E> it = super.listIterator(index);
        return new ListIterator<E>()
        {
            @Override
            public void add(E e)
            {
                it.add(e);
                markAsDirty(true);
            }

            @Override
            public boolean hasNext()
            {
                return it.hasNext();
            }

            @Override
            public boolean hasPrevious()
            {
                return it.hasPrevious();
            }

            @Override
            public E next()
            {
                return it.next();
            }

            @Override
            public int nextIndex()
            {
                return it.nextIndex();
            }

            @Override
            public E previous()
            {
                return it.previous();
            }

            @Override
            public int previousIndex()
            {
                return it.previousIndex();
            }

            @Override
            public void remove()
            {
                it.remove();
                markAsDirty(true);
            }

            @Override
            public void set(E e)
            {
                it.set(e);
                markAsDirty(true);
            }
        };
    }

    // @Override
    public List<E> subList(int fromIndex, int toIndex)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        return markAsDirty(super.removeAll(c));
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        return markAsDirty(super.retainAll(c));
    }
}
