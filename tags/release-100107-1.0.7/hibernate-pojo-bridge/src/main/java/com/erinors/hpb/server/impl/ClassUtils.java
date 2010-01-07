package com.erinors.hpb.server.impl;

import java.lang.reflect.Constructor;

import org.springframework.util.ReflectionUtils;

public class ClassUtils
{
    public static Constructor<?> getAccessibleNoArgConstructor(Class<?> clazz)
    {
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

    private ClassUtils()
    {
    }
}
