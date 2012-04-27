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

package com.erinors.hpb.tests.integration.case0005;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.erinors.hpb.test.SqlAppender;
import com.erinors.hpb.tests.integration.HpbIntegrationTestCase;

public class HibernateEmbeddedTest extends HpbIntegrationTestCase
{
    protected String getSpringConfig()
    {
        return "classpath:/com/erinors/hpb/tests/integration/case0005/test-setup.spring.xml";
    }

    @Test
    public void testRevertEmbeddedAnomaly()
    {
        populateDatabase();

        final Parent[] detached = new Parent[1];
        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                detached[0] = getPersistentObjectManager().clone(getEntityManager().find(Parent.class, 100L));
            }
        });

        detached[0].setEmbedded(new EmbeddableObject());

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Parent object = getPersistentObjectManager().merge(detached[0]);
                Parent merged = getEntityManager().merge(object);

                getEntityManager().flush();

                Assert.assertEquals(null, merged.getEmbedded());
                Assert.assertEquals(0, merged.getVersion());
            }
        });
    }

    @Test
    public void testNormalEmbeddedModificationNotReverted()
    {
        populateDatabase();

        final Parent[] detached = new Parent[1];
        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                detached[0] = getPersistentObjectManager().clone(getEntityManager().find(Parent.class, 100L));
            }
        });

        detached[0].setEmbedded(new EmbeddableObject("text"));

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Parent object = getPersistentObjectManager().merge(detached[0]);
                Parent merged = getEntityManager().merge(object);

                getEntityManager().flush();

                Assert.assertEquals(new EmbeddableObject("text"), merged.getEmbedded());
                Assert.assertEquals(1, merged.getVersion());
            }
        });
    }

    private void populateDatabase()
    {
        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Parent parent = (Parent) new Parent().presetId(100L);
                getEntityManager().persist(parent);
            }
        });

        Assert.assertEquals(Arrays.asList("insert into Parent (version, intValue, id) values (0, 0, 100)"), SqlAppender.get().getSql());
    }
}
