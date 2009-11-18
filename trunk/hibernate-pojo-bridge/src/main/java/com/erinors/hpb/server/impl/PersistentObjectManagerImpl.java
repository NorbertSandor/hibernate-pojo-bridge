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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.OrderComparator;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

import com.erinors.hpb.server.api.PersistentObjectManager;

/**
 * @author Norbert Sándor
 */
public class PersistentObjectManagerImpl implements PersistentObjectManager, BeanFactoryAware, ApplicationListener
{
    private ListableBeanFactory beanFactory;

    private List<? extends PersistentObjectHandler> handlers;

    private EntityManagerFactory entityManagerFactory;

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory)
    {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException
    {
        if (!(beanFactory instanceof ListableBeanFactory))
        {
            throw new RuntimeException("ListableBeanFactory expected but got " + beanFactory);
        }

        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event)
    {
        if (event instanceof ContextRefreshedEvent)
        {
            handlers = new ArrayList<PersistentObjectHandler>(
                    (Collection<? extends PersistentObjectHandler>) beanFactory.getBeansOfType(
                            PersistentObjectHandler.class).values());

            Collections.sort(handlers, new Comparator<PersistentObjectHandler>()
            {
                private OrderComparator orderComparator = new OrderComparator();

                @Override
                public int compare(PersistentObjectHandler o1, PersistentObjectHandler o2)
                {
                    return orderComparator.compare(o1, o2);
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T clone(T object)
    {
        return (T) new CloningContextImpl(handlers).clone(object);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T merge(T object)
    {
        return (T) new MergingContextImpl(handlers, getEntityManager()).merge(object);
    }

    private EntityManager getEntityManager()
    {
        return EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
    }
}
// FIXME support other persistent collection types
