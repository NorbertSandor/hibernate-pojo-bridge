/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.collection.PersistentSet;

import com.erinors.hpb.client.impl.UninitializedPersistentSet;

public class SetHandler extends AbstractPersistentObjectHandler
{
    @Override
    public Object clone(CloningContext context, Object object)
    {
        if (!(object instanceof Set))
        {
            return null;
        }

        Set<?> source = (Set<?>) object;
        Set<?> result;

        if (source instanceof PersistentSet && !((PersistentSet) source).wasInitialized())
        {
            result = new UninitializedPersistentSet();
            context.addProcessedObject(object, result);
        }
        else
        {
            Set<Object> set = new HashSet<Object>(source.size());
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
        if (!(object instanceof Set))
        {
            return null;
        }

        Set<?> source = (Set<?>) object;
        Set<?> result;

        if (source instanceof UninitializedPersistentSet)
        {
            result = new PersistentSet(context.getSessionImplementor());
            context.addProcessedObject(object, result);
        }
        else
        {
            Set<Object> set = new HashSet<Object>(source.size());
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
