/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
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
