/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.hibernate.collection.PersistentSortedMap;

import com.erinors.hpb.client.impl.UninitializedPersistentSortedMap;

public class SortedMapHandler extends AbstractPersistentObjectHandler
{
    @Override
    public Object clone(CloningContext context, Object object)
    {
        if (!(object instanceof SortedMap))
        {
            return null;
        }

        SortedMap<Object, Object> source = (SortedMap<Object, Object>) object;
        SortedMap<Object, Object> result;

        if (source instanceof PersistentSortedMap && !((PersistentSortedMap) source).wasInitialized())
        {
            result = new UninitializedPersistentSortedMap<Object, Object>(source.comparator());
            context.addProcessedObject(object, result);
        }
        else
        {
            SortedMap<Object, Object> map = new TreeMap<Object, Object>(source.comparator());
            context.addProcessedObject(object, map);

            for (Map.Entry<?, ?> entry : source.entrySet())
            {
                map.put(context.clone(entry.getKey()), context.clone(entry.getValue()));
            }

            result = map;
        }

        return result;
    }

    @Override
    public Object merge(MergingContext context, Object object)
    {
        if (!(object instanceof SortedMap))
        {
            return null;
        }

        SortedMap<Object, Object> source = (SortedMap<Object, Object>) object;
        SortedMap<Object, Object> result;

        if (source instanceof UninitializedPersistentSortedMap)
        {
            result = new PersistentSortedMap(context.getSessionImplementor(), source);
            context.addProcessedObject(object, result);
        }
        else
        {
            SortedMap<Object, Object> map = new TreeMap<Object, Object>(source.comparator());
            context.addProcessedObject(object, map);

            for (Map.Entry<?, ?> entry : source.entrySet())
            {
                map.put(context.merge(entry.getKey()), context.merge(entry.getValue()));
            }

            result = map;
        }

        return result;
    }

}
