/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

import javax.persistence.EntityManager;

import org.hibernate.engine.SessionImplementor;

public interface MergingContext extends Context
{
    void addProcessedObject(Object source, Object merged);

    Object merge(Object object);

    EntityManager getEntityManager();
    
    SessionImplementor getSessionImplementor();
}
