/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.collection.PersistentMap;

import com.erinors.hpb.client.impl.UninitializedPersistentMap;

public class MapHandler extends AbstractPersistentObjectHandler
{
    @Override
    public Object clone(CloningContext context, Object object)
    {
        if (!(object instanceof Map))
        {
            return null;
        }

        Map<?, ?> source = (Map<?, ?>) object;
        Map<?, ?> result;

        if (source instanceof PersistentMap && !((PersistentMap) source).wasInitialized())
        {
            result = new UninitializedPersistentMap<Object, Object>();
            context.addProcessedObject(object, result);
        }
        else
        {
            Map<Object, Object> map = new HashMap<Object, Object>(source.size());
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
        if (!(object instanceof Map))
        {
            return null;
        }

        Map<?, ?> source = (Map<?, ?>) object;
        Map<?, ?> result;

        if (source instanceof UninitializedPersistentMap)
        {
            result = new PersistentMap(context.getSessionImplementor());
            context.addProcessedObject(object, result);
        }
        else
        {
            Map<Object, Object> map = new HashMap<Object, Object>(source.size());
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
