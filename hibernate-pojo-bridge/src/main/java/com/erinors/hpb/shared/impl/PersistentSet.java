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
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Norbert Sándor
 */
public class PersistentSet<E> extends HashSet<E>
{
    private static final long serialVersionUID = 1L;

    private boolean dirty;

    public PersistentSet()
    {
    }

    public PersistentSet(int initialCapacity)
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
    public void clear()
    {
        boolean empty = isEmpty();
        super.clear();
        markAsDirty(!empty);
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
    public boolean remove(Object o)
    {
        return markAsDirty(super.remove(o));
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        return markAsDirty(super.removeAll(c));
    }

    @Override
    public boolean addAll(Collection<? extends E> c)
    {
        return markAsDirty(super.addAll(c));
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        return markAsDirty(super.retainAll(c));
    }
}
