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

package com.erinors.hpb.tests.integration;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.erinors.hpb.server.api.PersistentObjectManager;
import com.erinors.hpb.tests.SqlAppender;

public abstract class HpbIntegrationTestCase
{
    private ClassPathXmlApplicationContext applicationContext;

    @Before
    public void beforeTest()
    {
        System.setProperty("persistenceXmlLocation", getClass().getPackage().getName().replace('.', '/')
                + "/persistence.xml");
        applicationContext = new ClassPathXmlApplicationContext(new String[] { getSpringConfig() });

        SqlAppender.get().clearSql();
    }

    protected String getSpringConfig()
    {
        return "classpath:/com/erinors/hpb/tests/integration/test-setup.spring.xml";
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
        return applicationContext.getBean(PersistentObjectManager.class);
    }
}
