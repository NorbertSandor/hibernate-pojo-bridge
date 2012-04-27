package com.erinors.hpb.server.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.springframework.util.ReflectionUtils;

public class ClassUtils
{
    public static Constructor<?> getAccessibleInstanceConstructor(Class<?> clazz)
    {
        if (clazz.getEnclosingClass() != null && (clazz.getModifiers() & Modifier.STATIC) == 0)
        {
            throw new UnsupportedOperationException("Inner classes are not supported: " + clazz);
        }

        Constructor<?> constructor;
        try
        {
            constructor = clazz.getDeclaredConstructor();
        }
        catch (Exception e)
        {
            throw new RuntimeException("No no-arg constructor found: " + clazz, e);
        }

        ReflectionUtils.makeAccessible(constructor);

        return constructor;

    }

    // TODO cache
    public static void collectCloneableFields(Class<?> clazz, List<Field> fields)
    {
        for (Field field : clazz.getDeclaredFields())
        {
            int fieldModifiers = field.getModifiers();
            if ((fieldModifiers & Modifier.STATIC) == 0 && (fieldModifiers & Modifier.TRANSIENT) == 0)
            {
                fields.add(field);
            }
        }

        if (clazz.getSuperclass() != null)
        {
            collectCloneableFields(clazz.getSuperclass(), fields);
        }
    }

    private ClassUtils()
    {
    }
}
