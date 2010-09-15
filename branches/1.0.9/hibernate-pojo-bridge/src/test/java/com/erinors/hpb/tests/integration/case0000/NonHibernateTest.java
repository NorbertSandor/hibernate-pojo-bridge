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
        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Assert.assertNull(getPersistentObjectManager().merge(null));
                Assert.assertNull(getPersistentObjectManager().clone(null));
            }
        });
    }

    @Test
    public void testProcessTypes()
    {
        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
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

                Assert.assertArrayEquals(new int[] { 1, 2, 3 }, getPersistentObjectManager().merge(
                        new int[] { 1, 2, 3 }));
                Assert.assertArrayEquals(new Integer[] { 1, 2, 3 }, getPersistentObjectManager().merge(
                        new Integer[] { 1, 2, 3 }));
            }
        });
    }

    @Test
    public void testProcessBean()
    {
        getPersistenceService().executeTransactionally(new Runnable()
        {
            @Override
            public void run()
            {
                Bean original = new Bean(5);
                Bean cloned = getPersistentObjectManager().clone(original);

                Assert.assertTrue(original != cloned);
                Assert.assertEquals(original.getValue(), cloned.getValue());

                Bean merged = getPersistentObjectManager().merge(cloned);

                Assert.assertTrue(cloned != merged);
                Assert.assertEquals(cloned.getValue(), merged.getValue());

                Assert.assertArrayEquals(new Bean[] { new Bean(1), new Bean(2), new Bean(3) },
                        getPersistentObjectManager().merge(new Bean[] { new Bean(1), new Bean(2), new Bean(3) }));
            }
        });
    }
}
