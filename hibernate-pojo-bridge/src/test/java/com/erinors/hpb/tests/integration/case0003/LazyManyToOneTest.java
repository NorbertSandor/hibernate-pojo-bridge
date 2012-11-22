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

package com.erinors.hpb.tests.integration.case0003;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.junit.Assert;
import org.junit.Test;

import com.erinors.hpb.server.api.PersistentObjectManager;
import com.erinors.hpb.tests.EmptyPersistentObjectManager;
import com.erinors.hpb.tests.SqlAppender;
import com.erinors.hpb.tests.integration.HpbIntegrationTestCase;

public class LazyManyToOneTest extends HpbIntegrationTestCase
{
    @Test
    public void testCloneAndMergeWithEmptyPersistentObjectManager()
    {
        testCloneAndMerge(new EmptyPersistentObjectManager());
    }

    @Test
    public void testCloneAndMergeWithRealPersistentObjectManager()
    {
        testCloneAndMerge(getPersistentObjectManager());
    }

    private void testCloneAndMerge(final PersistentObjectManager persistentObjectManager)
    {
        //
        // Populate database
        //

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Referenced referenced = new Referenced();
                referenced.setValue(5);
                referenced.presetId(101);

                Parent parent = (Parent) new Parent().presetId(100);
                parent.setReferenced(referenced);
                getEntityManager().persist(parent);
            }
        });

        Assert.assertEquals(Arrays.asList( //
                "insert into Referenced (version, value, id) values (0, 5, 101)", //
                "insert into Parent (version, referenced_id, id) values (0, 101, 100)" //
        ), SqlAppender.get().getSql());

        //
        // Clone and merge
        //

        final Parent[] detached = new Parent[1];

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Parent parent = getEntityManager().find(Parent.class, 100L);
                Assert.assertTrue(parent.getReferenced() instanceof HibernateProxy);

                HibernateProxy referenced = (HibernateProxy) parent.getReferenced();
                Assert.assertTrue(referenced.getHibernateLazyInitializer().isUninitialized());

                detached[0] = persistentObjectManager.clone(parent);
            }
        });

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Parent parent = persistentObjectManager.merge(detached[0]);
                Assert.assertTrue(parent.getReferenced() instanceof HibernateProxy);

                HibernateProxy referenced = (HibernateProxy) parent.getReferenced();
                Assert.assertTrue(referenced.getHibernateLazyInitializer().isUninitialized());
            }
        });

        List<String> sql = SqlAppender.get().getSql();
        Assert.assertEquals(1, sql.size());
        Assert
                .assertTrue(sql
                        .get(0)
                        .matches(
                                "select parent\\d+_\\.id as id\\d+_\\d+_, parent\\d+_\\.version as version\\d+_\\d+_, parent\\d+_\\.referenced_id as referenced\\d+_\\d+_\\d+_ from Parent parent\\d+_ where parent\\d+_\\.id=100"));
    }

    @Test
    public void testModifyProxyPropertyWithEmptyPersistentObjectManager()
    {
        testModifyProxyProperty(new EmptyPersistentObjectManager());
    }

    @Test
    public void testModifyProxyPropertyWithRealPersistentObjectManager()
    {
        testModifyProxyProperty(getPersistentObjectManager());
    }

    private void testModifyProxyProperty(final PersistentObjectManager persistentObjectManager)
    {
        //
        // Populate database
        //

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Referenced referenced = new Referenced();
                referenced.setValue(5);
                referenced.presetId(101);

                Parent parent = (Parent) new Parent().presetId(100);
                parent.setReferenced(referenced);
                getEntityManager().persist(parent);

                Referenced otherReferenced = new Referenced();
                otherReferenced.setValue(7);
                otherReferenced.presetId(102);
                getEntityManager().persist(otherReferenced);
            }
        });

        Assert.assertEquals(Arrays.asList( //
                "insert into Referenced (version, value, id) values (0, 5, 101)", //
                "insert into Parent (version, referenced_id, id) values (0, 101, 100)", //
                "insert into Referenced (version, value, id) values (0, 7, 102)" //
        ), SqlAppender.get().getSql());

        //
        // Clone
        //

        final Parent[] detachedParent = new Parent[1];
        final Referenced[] detachedOtherReferenced = new Referenced[1];

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Parent parent = getEntityManager().find(Parent.class, 100L);
                Assert.assertTrue(parent.getReferenced() instanceof HibernateProxy
                        && !Hibernate.isInitialized(parent.getReferenced()));
                detachedParent[0] = persistentObjectManager.clone(parent);

                Referenced otherReferenced = getEntityManager().getReference(Referenced.class, 102L);
                Assert.assertFalse(Hibernate.isInitialized(otherReferenced));
                detachedOtherReferenced[0] = persistentObjectManager.clone(otherReferenced);
            }
        });

        SqlAppender.get().clearSql();

        //
        // Modify proxy property
        //

        detachedParent[0].setReferenced(detachedOtherReferenced[0]);

        //
        // Merge
        //

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Parent parent = persistentObjectManager.merge(detachedParent[0]);
                Assert.assertTrue(parent.getReferenced() instanceof HibernateProxy
                        && !Hibernate.isInitialized(parent.getReferenced()) && parent.getReferenced().getId() == 102L);

                getEntityManager().merge(parent);
            }
        });

        List<String> sql = SqlAppender.get().getSql();
        Assert.assertEquals(2, sql.size());
        Assert
                .assertTrue(sql
                        .get(0)
                        .matches(
                                "select parent\\d+_\\.id as id\\d+_\\d+_, parent\\d+_\\.version as version\\d+_\\d+_, parent\\d+_\\.referenced_id as referenced\\d+_\\d+_\\d+_, referenced\\d+_\\.id as id\\d+_\\d+_, referenced\\d+_\\.version as version\\d+_\\d+_, referenced\\d+_\\.value as value\\d+_\\d+_ from Parent parent\\d+_ left outer join Referenced referenced\\d+_ on parent\\d+_\\.referenced_id=referenced\\d+_\\.id where parent\\d+_\\.id=100"));
        Assert.assertTrue(sql.get(1).matches(
                "update Parent set version=1, referenced_id=102 where id=100 and version=0"));
    }
}
