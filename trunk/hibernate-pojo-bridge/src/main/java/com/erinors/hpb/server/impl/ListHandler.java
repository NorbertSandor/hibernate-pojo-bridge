/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.collection.PersistentList;

import com.erinors.hpb.client.impl.UninitializedPersistentList;

public class ListHandler extends AbstractPersistentObjectHandler
{
    @Override
    public Object clone(CloningContext context, Object object)
    {
        if (!(object instanceof List))
        {
            return null;
        }

        List<?> source = (List<?>) object;
        List<?> result;

        if (source instanceof PersistentList && !((PersistentList) source).wasInitialized())
        {
            result = new UninitializedPersistentList();
            context.addProcessedObject(object, result);
        }
        else
        {
            List<Object> list = new ArrayList<Object>(source.size());
            context.addProcessedObject(object, list);

            for (Object element : source)
            {
                list.add(context.clone(element));
            }

            result = list;
        }

        return result;
    }

    @Override
    public Object merge(MergingContext context, Object object)
    {
        if (!(object instanceof List))
        {
            return null;
        }

        List<?> source = (List<?>) object;
        List<?> result;

        if (source instanceof UninitializedPersistentList)
        {
            result = new PersistentList(context.getSessionImplementor());
            context.addProcessedObject(object, result);
        }
        else
        {
            List<Object> list = new ArrayList<Object>(source.size());
            context.addProcessedObject(object, list);

            for (Object element : source)
            {
                list.add(context.merge(element));
            }

            result = list;
        }

        return result;
    }
}
