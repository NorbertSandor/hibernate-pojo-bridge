/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.erinors.hpb.server.api.PersistentObjectManager;

public class HpbIntegrationTestCase
{
    private ClassPathXmlApplicationContext applicationContext;

    @Before
    public void beforeTest()
    {
	System.setProperty("persistenceXmlLocation", getClass().getPackage().getName().replace('.', '/')
		+ "/persistence.xml");
	applicationContext = new ClassPathXmlApplicationContext(new String[]
	{ "classpath:/com/erinors/hpb/tests/integration/test-setup.spring.xml" });

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

    protected static void assertHaveSameDml(List<String> sql1, List<String> sql2)
    {
	if (sql1.size() != sql2.size())
	{
	    Assert.fail();
	}

	for (int i = 0; i < sql1.size(); i++)
	{
	    String s1 = sql1.get(i);
	    String s2 = sql2.get(i);

	    if ((!s1.startsWith("select") || !s2.startsWith("select")) && !s1.equals(s2))
	    {
		Assert.fail();
	    }
	}
    }
}
