/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

public interface Context
{
    Object getProcessedObject(Object source);

    void addProcessedObject(Object source, Object processed);
}
