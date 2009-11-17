/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Norbert Sándor
 */
public abstract class AbstractContext implements Context
{
    private final Map<Object, Object> processedObjects = new IdentityHashMap<Object, Object>();

    private final List<? extends PersistentObjectHandler> handlers;

    protected AbstractContext(List<? extends PersistentObjectHandler> handlers)
    {
	this.handlers = handlers;
    }

    @Override
    public void addProcessedObject(Object source, Object processed)
    {
	if (processedObjects.containsKey(source))
	{
	    throw new IllegalStateException("Object already processed: " + source);
	}

	processedObjects.put(source, processed);
    }

    @Override
    public Object getProcessedObject(Object source)
    {
	return processedObjects.get(source);
    }

    protected List<? extends PersistentObjectHandler> getHandlers()
    {
	return handlers;
    }
}
