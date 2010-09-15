/*
 * Copyright 2009 Norbert Sándor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
