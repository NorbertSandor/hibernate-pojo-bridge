/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration.case0001;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.collection.PersistentSet;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.erinors.hpb.server.api.PersistentObjectManager;
import com.erinors.hpb.tests.integration.EmptyPersistentObjectManager;
import com.erinors.hpb.tests.integration.HpbIntegrationTestCase;
import com.erinors.hpb.tests.integration.SqlAppender;

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
	assertHaveSameDml(sqls.get(0), sqls.get(1));
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
