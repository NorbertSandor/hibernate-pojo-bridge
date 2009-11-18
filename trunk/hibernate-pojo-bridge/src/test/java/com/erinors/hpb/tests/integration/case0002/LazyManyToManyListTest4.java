/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration.case0002;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.collection.PersistentList;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.erinors.hpb.server.api.PersistentObjectManager;
import com.erinors.hpb.test.EmptyPersistentObjectManager;
import com.erinors.hpb.test.HpbTestUtils;
import com.erinors.hpb.test.SqlAppender;
import com.erinors.hpb.tests.integration.HpbIntegrationTestCase;

public class LazyManyToManyListTest4 extends HpbIntegrationTestCase
{
    private static List<List<String>> sqls = new ArrayList<List<String>>();

    @Test
    public void testAddAndRemove()
    {
        testAddAndRemove(new EmptyPersistentObjectManager());
        List<String> sql = SqlAppender.get().getSql();
        sqls.add(sql);
    }

    @Test
    public void testAddAndRemove2()
    {
        testAddAndRemove(getPersistentObjectManager());
        List<String> sql = SqlAppender.get().getSql();
        sqls.add(sql);
    }

    @AfterClass
    public static void afterClass()
    {
        HpbTestUtils.assertHaveSameDml(sqls.get(0), sqls.get(1));
    }

    private void testAddAndRemove(final PersistentObjectManager persistentObjectManager)
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

        Assert.assertEquals(Arrays.asList("insert into Parent (version, id) values ('0', '100')",
                "insert into Child (version, value, id) values ('0', '0', '101')",
                "insert into Child (version, value, id) values ('0', '1', '102')",
                "insert into Child (version, value, id) values ('0', '2', '103')",
                "insert into Parent_Child (Parent_id, childIndex, children_id) values ('100', '0', '101')",
                "insert into Parent_Child (Parent_id, childIndex, children_id) values ('100', '1', '102')",
                "insert into Parent_Child (Parent_id, childIndex, children_id) values ('100', '2', '103')"),
                SqlAppender.get().getSql());

        //
        // Clone
        //

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Parent parent = getEntityManager().find(Parent.class, 100L);
                Assert.assertTrue(parent.getChildren() instanceof PersistentList);
                Assert.assertFalse(((PersistentList) parent.getChildren()).wasInitialized());
            }
        });

        SqlAppender.get().clearSql();

        //
        // Replace set
        //

        detached[0].setChildren(new ArrayList<Child>());

        //
        // Merge
        //

        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                getEntityManager().merge(persistentObjectManager.merge(detached[0]));
            }
        });

        List<String> sql = SqlAppender.get().getSql();
        Assert.assertEquals(3, sql.size());
        Assert.assertTrue(sql.get(0).startsWith("select"));
        Assert.assertEquals("update Parent set version='1' where id='100' and version='0'", sql.get(1));
        Assert.assertEquals("delete from Parent_Child where Parent_id='100'", sql.get(2));
    }
}
