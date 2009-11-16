/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration.case0004;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.erinors.hpb.server.api.PersistentObjectManager;
import com.erinors.hpb.tests.integration.EmptyPersistentObjectManager;
import com.erinors.hpb.tests.integration.HpbIntegrationTestCase;
import com.erinors.hpb.tests.integration.SqlAppender;

public class MergeUnmodifiedEntityTest extends HpbIntegrationTestCase
{
    @Test
    public void testWithEmptyPersistentObjectManager()
    {
        doTest(new EmptyPersistentObjectManager());
    }

    @Test
    public void testWithRealPersistentObjectManager()
    {
        doTest(getPersistentObjectManager());
    }

    private void doTest(final PersistentObjectManager persistentObjectManager)
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

                detached[0] = parent;
            }
        });

        detached[0] = persistentObjectManager.clone(detached[0]);

        Assert.assertEquals(Arrays.asList("insert into Parent (version, id) values ('0', '100')",
                "insert into Child (version, value, id) values ('0', '1', '102')",
                "insert into Child (version, value, id) values ('0', '2', '103')",
                "insert into Child (version, value, id) values ('0', '0', '101')",
                "insert into Parent_Child (Parent_id, children_id) values ('100', '102')",
                "insert into Parent_Child (Parent_id, children_id) values ('100', '103')",
                "insert into Parent_Child (Parent_id, children_id) values ('100', '101')"), SqlAppender.get().getSql());

        //
        // Merge unmodified
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
        Assert.assertEquals(1, sql.size());
        Assert.assertTrue(sql.get(0).startsWith("select"));
    }
}
