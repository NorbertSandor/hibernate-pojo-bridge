/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.erinors.hpb.server.api.PersistentObjectManager;
import com.erinors.hpb.test.SqlAppender;

public class HpbIntegrationTestCase
{
    private ClassPathXmlApplicationContext applicationContext;

    @Before
    public void beforeTest()
    {
        System.setProperty("persistenceXmlLocation", getClass().getPackage().getName().replace('.', '/')
                + "/persistence.xml");
        applicationContext = new ClassPathXmlApplicationContext(
                new String[] { "classpath:/com/erinors/hpb/tests/integration/test-setup.spring.xml" });

        SqlAppender.get().clearSql();
    }

    @After
    public void afterTest()
    {
        if (applicationContext != null)
        {
            applicationContext.close();
        }

        SqlAppender.get().clearSql();
    }

    protected PersistenceService getPersistenceService()
    {
        return (PersistenceService) applicationContext.getBean("persistenceService");
    }

    protected EntityManager getEntityManager()
    {
        return getPersistenceService().getEntityManager();
    }

    protected PersistentObjectManager getPersistentObjectManager()
    {
        return (PersistentObjectManager) applicationContext.getBean("persistentObjectManager");
    }
}
