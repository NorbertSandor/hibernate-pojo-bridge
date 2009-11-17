/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.api;

/**
 * @author Norbert Sándor
 */
public interface PersistentObjectManager
{
    <T> T clone(T object);

    <T> T merge(T object);
}
