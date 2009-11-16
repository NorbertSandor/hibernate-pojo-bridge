package com.erinors.hpb.client.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

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
        super.clear();
        markAsDirty(true);
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
