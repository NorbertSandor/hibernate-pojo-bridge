/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.client.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class UninitializedPersistentList<E> extends UninitializedPersistentCollection<E> implements List<E>, Serializable
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
