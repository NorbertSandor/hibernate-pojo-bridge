/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.engine.SessionImplementor;

/**
 * @author Norbert Sándor
 */
public class MergingContextImpl extends AbstractContext implements MergingContext
{
    private final EntityManager entityManager;

    protected MergingContextImpl(List<? extends PersistentObjectHandler> handlers, EntityManager entityManager)
    {
        super(handlers);
        this.entityManager = entityManager;
    }

    @Override
    public Object merge(Object object)
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
            Object merged = null;
            for (PersistentObjectHandler handler : getHandlers())
            {
                merged = handler.merge(this, object);
                if (merged != null)
                {
                    return merged;
                }
            }

            throw new RuntimeException("Cannot merge object of unsupported type: " + object);
        }
    }

    @Override
    public EntityManager getEntityManager()
    {
        return entityManager;
    }
    
    @Override
    public SessionImplementor getSessionImplementor()
    {
        assert getEntityManager().getDelegate() instanceof SessionImplementor : "Unsupported EntityManager, an instance of SessionImplementor is expected but got "
                + getEntityManager();
        return (SessionImplementor) getEntityManager().getDelegate();
    }
}
