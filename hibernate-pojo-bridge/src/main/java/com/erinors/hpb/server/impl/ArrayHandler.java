package com.erinors.hpb.server.impl;

import java.lang.reflect.Array;

import org.springframework.stereotype.Service;

@Service
public class ArrayHandler extends AbstractPersistentObjectHandler
{
    public ArrayHandler()
    {
        super(150);
    }

    @Override
    public Object clone(CloningContext context, Object object)
    {
        if (!object.getClass().isArray())
        {
            return null;
        }

        int length = Array.getLength(object);
        Class<?> componentType = object.getClass().getComponentType();
        Object result = Array.newInstance(componentType, length);
        context.addProcessedObject(object, result);

        for (int i = 0; i < length; i++)
        {
            Array.set(result, i, context.clone(Array.get(object, i)));
        }

        return result;
    }

    @Override
    public Object merge(MergingContext context, Object object)
    {
        if (!object.getClass().isArray())
        {
            return null;
        }

        int length = Array.getLength(object);
        Class<?> componentType = object.getClass().getComponentType();
        Object result = Array.newInstance(componentType, length);
        context.addProcessedObject(object, result);

        for (int i = 0; i < length; i++)
        {
            Array.set(result, i, context.merge(Array.get(object, i)));
        }

        return result;
    }
}
// TODO code duplication, the 2 methods above are almost the same
