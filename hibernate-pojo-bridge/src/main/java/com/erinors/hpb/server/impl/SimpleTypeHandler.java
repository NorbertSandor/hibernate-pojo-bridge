/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Norbert Sándor
 */
public class SimpleTypeHandler extends AbstractPersistentObjectHandler
{
    private static final Set<Class<?>> immutableTypeClasses = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]
    { Boolean.class, Byte.class, Character.class, Double.class, Float.class, Integer.class, Long.class, Short.class,
	    String.class }));

    @Override
    public Object clone(CloningContext context, Object object)
    {
	return copy(object);
    }

    @Override
    public Object merge(MergingContext context, Object object)
    {
	return copy(object);
    }

    private Object copy(Object object)
    {
	if (immutableTypeClasses.contains(object.getClass()) || object instanceof Enum)
	{
	    return object;
	}
	else if (object instanceof Date)
	{
	    return ((Date) object).clone(); // FIXME ez külön handler legyen
	}
	else
	{
	    return null;
	}
    }
}
