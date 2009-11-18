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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.springframework.util.ReflectionUtils;

import com.erinors.hpb.client.api.HibernateProxyGwtSupport;

/**
 * @author Norbert Sándor
 */
public class JavaBeanHandler extends AbstractPersistentObjectHandler
{
    private interface ObjectCopier
    {
        Object processObject(Object object);
    }

    @Override
    public Object clone(final CloningContext context, Object object)
    {
        final Object effectiveObject;
        if (object instanceof HibernateProxy)
        {
            LazyInitializer lazyInitializer = ((HibernateProxy) object).getHibernateLazyInitializer();

            if (!lazyInitializer.isUninitialized())
            {
                throw new RuntimeException("Uninitialized proxies are not supported by this handler: " + object);
            }

            effectiveObject = lazyInitializer.getImplementation();
        }
        else
        {
            effectiveObject = object;
        }

        return copyBean(effectiveObject, new ObjectCopier()
        {
            @Override
            public Object processObject(Object object)
            {
                return context.clone(object);
            }
        }, context);
    }

    @Override
    public Object merge(final MergingContext context, Object object)
    {
        if (object instanceof HibernateProxyGwtSupport
                && ((HibernateProxyGwtSupport) object).isUninitializedHibernateProxy())
        {
            throw new RuntimeException("Uninitialized proxies are not supported by this handler: " + object);
        }

        return copyBean(object, new ObjectCopier()
        {
            @Override
            public Object processObject(Object object)
            {
                return context.merge(object);
            }
        }, context);
    }

    private Object copyBean(Object object, ObjectCopier objectCopier, Context context)
    {
        Object result;
        try
        {
            Constructor<?> constructor = object.getClass().getConstructor();
            ReflectionUtils.makeAccessible(constructor);

            result = constructor.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e); // FIXME
        }

        context.addProcessedObject(object, result);

        for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(object))
        {
            if (pd.getWriteMethod() != null && pd.getReadMethod() != null)
            {
                // TODO trace

                try
                {
                    Object value = PropertyUtils.getSimpleProperty(object, pd.getName());
                    Object processedValue = objectCopier.processObject(value);
                    PropertyUtils.setProperty(result, pd.getName(), processedValue);
                }
                catch (Exception e)
                {
                    throw new RuntimeException(e); // FIXME
                }
            }
            else
            {
                // TODO trace
            }
        }

        return result;
    }
}
