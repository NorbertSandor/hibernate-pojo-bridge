/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.collection.PersistentSet;
import org.hibernate.collection.PersistentSortedSet;

import com.erinors.hpb.client.impl.UninitializedPersistentSet;
import com.erinors.hpb.client.impl.UninitializedPersistentSortedSet;

public class SortedSetHandler extends AbstractPersistentObjectHandler
{
    @Override
    public Object clone(CloningContext context, Object object)
    {
        if (!(object instanceof SortedSet))
        {
            return null;
        }

        SortedSet<Object> source = (SortedSet<Object>) object;
        SortedSet<Object> result;

        if (source instanceof PersistentSortedSet && !((PersistentSortedSet) source).wasInitialized())
        {
            result = new UninitializedPersistentSortedSet<Object>(source.comparator());
            context.addProcessedObject(object, result);
        }
        else
        {
            SortedSet<Object> set = new TreeSet<Object>(source.comparator());
            context.addProcessedObject(object, set);

            for (Object element : source)
            {
                set.add(context.clone(element));
            }

            result = set;
        }

        return result;
    }

    @Override
    public Object merge(MergingContext context, Object object)
    {
        if (!(object instanceof SortedSet))
        {
            return null;
        }

        SortedSet<Object> source = (SortedSet<Object>) object;
        SortedSet<Object> result;

        if (source instanceof UninitializedPersistentSortedSet)
        {
            result = new PersistentSortedSet(context.getSessionImplementor(), source);
            context.addProcessedObject(object, result);
        }
        else
        {
            SortedSet<Object> set = new TreeSet<Object>(source.comparator());
            context.addProcessedObject(object, set);

            for (Object element : source)
            {
                set.add(context.merge(element));
            }

            result = set;
        }

        return result;
    }

}
