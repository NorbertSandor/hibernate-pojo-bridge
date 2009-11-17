/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

/**
 * @author Norbert Sándor
 */
public interface CloningContext extends Context
{
    void addProcessedObject(Object source, Object cloned);

    Object clone(Object object);
}
