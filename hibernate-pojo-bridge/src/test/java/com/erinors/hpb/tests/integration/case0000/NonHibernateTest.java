/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration.case0000;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.erinors.hpb.tests.integration.HpbIntegrationTestCase;

public class NonHibernateTest extends HpbIntegrationTestCase
{
    @Test
    public void testProcessNull()
    {
	Assert.assertNull(getPersistentObjectManager().merge(null));
	Assert.assertNull(getPersistentObjectManager().clone(null));
    }

    @Test
    public void testProcessSimpleTypes()
    {
	Assert.assertEquals(Boolean.TRUE, getPersistentObjectManager().clone(Boolean.TRUE));
	Assert.assertEquals(Boolean.FALSE, getPersistentObjectManager().clone(Boolean.FALSE));
	Assert.assertEquals(new Byte((byte) 0), getPersistentObjectManager().clone(new Byte((byte) 0)));
	Assert.assertEquals(new Character('a'), getPersistentObjectManager().clone(new Character('a')));
	Assert.assertEquals(new Double(0.0), getPersistentObjectManager().clone(new Double(0.0)));
	Assert.assertEquals(new Float(0.0f), getPersistentObjectManager().clone(new Float(0.0f)));
	Assert.assertEquals(new Integer(0), getPersistentObjectManager().clone(new Integer(0)));
	Assert.assertEquals(new Long(0L), getPersistentObjectManager().clone(new Long(0L)));
	Assert.assertEquals(new Short((short) 0), getPersistentObjectManager().clone(new Short((short) 0)));
	Assert.assertEquals(new String("abc"), getPersistentObjectManager().clone(new String("abc")));
	Assert.assertEquals(new Date(10000), getPersistentObjectManager().clone(new Date(10000)));

	Assert.assertEquals(Boolean.TRUE, getPersistentObjectManager().merge(Boolean.TRUE));
	Assert.assertEquals(Boolean.FALSE, getPersistentObjectManager().merge(Boolean.FALSE));
	Assert.assertEquals(new Byte((byte) 0), getPersistentObjectManager().merge(new Byte((byte) 0)));
	Assert.assertEquals(new Character('a'), getPersistentObjectManager().merge(new Character('a')));
	Assert.assertEquals(new Double(0.0), getPersistentObjectManager().merge(new Double(0.0)));
	Assert.assertEquals(new Float(0.0f), getPersistentObjectManager().merge(new Float(0.0f)));
	Assert.assertEquals(new Integer(0), getPersistentObjectManager().merge(new Integer(0)));
	Assert.assertEquals(new Long(0L), getPersistentObjectManager().merge(new Long(0L)));
	Assert.assertEquals(new Short((short) 0), getPersistentObjectManager().merge(new Short((short) 0)));
	Assert.assertEquals(new String("abc"), getPersistentObjectManager().merge(new String("abc")));
	Assert.assertEquals(new Date(10000), getPersistentObjectManager().merge(new Date(10000)));
    }

    @Test
    public void testProcessBean()
    {
	Bean original = new Bean(5);
	Bean cloned = getPersistentObjectManager().clone(original);

	Assert.assertTrue(original != cloned);
	Assert.assertEquals(original.getValue(), cloned.getValue());

	Bean merged = getPersistentObjectManager().merge(cloned);

	Assert.assertTrue(cloned != merged);
	Assert.assertEquals(cloned.getValue(), merged.getValue());
    }
}
