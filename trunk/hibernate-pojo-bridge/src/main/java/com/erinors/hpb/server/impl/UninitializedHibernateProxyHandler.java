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

import java.lang.reflect.Constructor;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.springframework.stereotype.Service;

import com.erinors.hpb.shared.api.HibernateProxyPojoSupport;

/**
 * @author Norbert Sándor
 */
@Service
public class UninitializedHibernateProxyHandler extends AbstractPersistentObjectHandler
{
    public UninitializedHibernateProxyHandler()
    {
        super(300);
    }

    @Override
    public Object clone(CloningContext context, Object object)
    {
        //
        // Check if the object is an uninitialized hibernate proxy, return false otherwise
        //

        if (!(object instanceof HibernateProxy))
        {
            return null;
        }

        HibernateProxy hibernateProxy = (HibernateProxy) object;
        LazyInitializer lazyInitializer = hibernateProxy.getHibernateLazyInitializer();

        if (!lazyInitializer.isUninitialized())
        {
            return null;
        }

        //
        // Check if the proxied object can be cloned
        //

        Class<?> persistentClass = lazyInitializer.getPersistentClass();
        if (!HibernateProxyPojoSupport.class.isAssignableFrom(persistentClass))
        {
            // TODO tesztet rá
            throw new RuntimeException("Uninitialized hibernate proxy should implement "
                    + HibernateProxyPojoSupport.class.getName() + " to be cloneable: " + persistentClass);
        }

        //
        // Clone
        //

        HibernateProxyPojoSupport result;
        try
        {
            Constructor<?> constructor = ClassUtils.getAccessibleInstanceConstructor(persistentClass);
            result = (HibernateProxyPojoSupport) constructor.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Cannot instantiate: " + persistentClass.getClass(), e);
        }

        result.setUninitializedHibernateProxy(true);
        result.setUninitializedHibernateProxyId(lazyInitializer.getIdentifier());

        context.addProcessedObject(object, result);

        return result;
    }

    @Override
    public Object merge(MergingContext context, Object object)
    {
        //
        // Check object
        //

        if (!(object instanceof HibernateProxyPojoSupport))
        {
            return null;
        }

        HibernateProxyPojoSupport hpgs = (HibernateProxyPojoSupport) object;

        if (!hpgs.isUninitializedHibernateProxy())
        {
            return null;
        }

        //
        // Merge
        //

        Object result = context.getEntityManager().getReference(object.getClass(),
                hpgs.getUninitializedHibernateProxyId());

        context.addProcessedObject(object, result);

        return result;
    }

}
