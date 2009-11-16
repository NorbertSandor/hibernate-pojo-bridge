/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration;

import com.erinors.hpb.server.api.PersistentObjectManager;

public class EmptyPersistentObjectManager implements PersistentObjectManager
{
    @Override
    public <T> T clone(T object)
    {
	return object;
    }

    @Override
    public <T> T merge(T object)
    {
	return object;
    }
}
