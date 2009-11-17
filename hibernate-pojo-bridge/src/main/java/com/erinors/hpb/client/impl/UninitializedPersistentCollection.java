/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
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
