/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

import java.util.List;

/**
 * @author Norbert Sándor
 */
public class CloningContextImpl extends AbstractContext implements CloningContext
{
    protected CloningContextImpl(List<? extends PersistentObjectHandler> handlers)
    {
	super(handlers);
    }

    @Override
    public Object clone(Object object)
    {
	if (object == null)
	{
	    return null;
	}

	Object alreadyCloned = getProcessedObject(object);

	if (alreadyCloned != null)
	{
	    return alreadyCloned;
	}
	else
	{
	    Object cloned = null;
	    for (PersistentObjectHandler handler : getHandlers())
	    {
		cloned = handler.clone(this, object);
		if (cloned != null)
		{
		    return cloned;
		}
	    }

	    throw new RuntimeException("Cannot clone object of unsupported type: " + object);
	}
    }
}
