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

package com.erinors.hpb.tests.integration.case0001;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.collection.internal.PersistentSet;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.erinors.hpb.server.api.PersistentObjectManager;
import com.erinors.hpb.tests.EmptyPersistentObjectManager;
import com.erinors.hpb.tests.HpbTestUtils;
import com.erinors.hpb.tests.SqlAppender;
import com.erinors.hpb.tests.integration.HpbIntegrationTestCase;

public class LazyManyToManySetTest2 extends HpbIntegrationTestCase
{
    private static List<List<String>> sqls = new ArrayList<List<String>>();

    @Test
    public void testAdd()
    {
        testAdd(new EmptyPersistentObjectManager());
        List<String> sql = SqlAppender.get().getSql();
        sqls.add(sql);
    }

    @Test
    public void testAdd2()
    {
        testAdd(getPersistentObjectManager());
        List<String> sql = SqlAppender.get().getSql();
        sqls.add(sql);
    }

    @AfterClass
    public static void afterClass()
    {
        HpbTestUtils.assertHaveSameDml(sqls.get(0), sqls.get(1));
    }

    private void testAdd(final PersistentObjectManager persistentObjectManager)
    {
        //
        // Populate database
        //

        final Parent[] detached = new Parent[1];

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Parent parent = (Parent) new Parent().presetId(100);
                parent.getChildren().add((Child) new Child(0).presetId(101));
                parent.getChildren().add((Child) new Child(1).presetId(102));
                parent.getChildren().add((Child) new Child(2).presetId(103));
                getEntityManager().persist(parent);

                detached[0] = persistentObjectManager.clone(parent);
            }
        });

        //
        // Check laziness
        //

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Parent parent = getEntityManager().find(Parent.class, 100L);
                Assert.assertTrue(parent.getChildren() instanceof PersistentSet);
                Assert.assertFalse(((PersistentSet) parent.getChildren()).wasInitialized());

                parent.getChildren().iterator().next();

                Assert.assertTrue(((PersistentSet) parent.getChildren()).wasInitialized());
            }
        });

        //
        // Merge detached and modified parent
        //

        final Child removedChild = detached[0].getChildren().iterator().next();
        detached[0].getChildren().remove(removedChild);

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                getEntityManager().merge(persistentObjectManager.merge(detached[0]));
            }
        });
    }
}
