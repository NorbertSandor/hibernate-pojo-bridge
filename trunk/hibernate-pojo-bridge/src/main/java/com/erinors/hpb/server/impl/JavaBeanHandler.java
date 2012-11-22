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
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.springframework.util.ReflectionUtils;

import com.erinors.hpb.shared.api.HibernateProxyPojoSupport;

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

            if (lazyInitializer.isUninitialized())
            {
                throw new RuntimeException("Uninitialized proxies are not supported by this handler: " + object);
            }

            effectiveObject = lazyInitializer.getImplementation();

            Object alreadyProcessedObject = context.getProcessedObject(effectiveObject);
            if (alreadyProcessedObject != null)
            {
                return alreadyProcessedObject;
            }
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
        if (object instanceof HibernateProxyPojoSupport
                && ((HibernateProxyPojoSupport) object).isUninitializedHibernateProxy())
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
        Class<?> clazz = object.getClass();

        Object result;
        try
        {
            Constructor<?> constructor = ClassUtils.getAccessibleInstanceConstructor(clazz);
            result = constructor.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Cannot instantiate: " + object.getClass(), e);
        }

        context.addProcessedObject(object, result);

        List<Field> fields = new LinkedList<Field>();
        ClassUtils.collectCloneableFields(clazz, fields);

        for (Field field : fields)
        {
            try
            {
                ReflectionUtils.makeAccessible(field);

                Object fieldValue = field.get(object);
                Object processedFieldValue = objectCopier.processObject(fieldValue);
                field.set(result, processedFieldValue);
            }
            catch (Exception e)
            {
                throw new RuntimeException("Cannot copy field: " + field, e);
            }
        }

        return result;
    }
}
