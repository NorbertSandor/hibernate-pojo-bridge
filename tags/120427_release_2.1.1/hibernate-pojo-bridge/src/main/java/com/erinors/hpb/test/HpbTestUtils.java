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

package com.erinors.hpb.test;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.springframework.util.ReflectionUtils;

import com.erinors.hpb.server.impl.ClassUtils;

public class HpbTestUtils
{
    public static void assertHaveSameDml(List<String> sql1, List<String> sql2)
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

    private static boolean equal(Object a, Object b)
    {
        return a == b || (a != null && a.equals(b));
    }

    public static void assertEqualsByCloneableFields(Object o1, Object o2)
    {
        if (o1 == null || o2 == null || o1.getClass() != o2.getClass())
        {
            throw new IllegalArgumentException("Non-null objects of same class expected but got: " + o1 + ", " + o2);
        }

        List<Field> fields = new LinkedList<Field>();
        ClassUtils.collectCloneableFields(o1.getClass(), fields);

        for (Field field : fields)
        {
            try
            {
                ReflectionUtils.makeAccessible(field);

                Object fieldValue1 = field.get(o1);
                Object fieldValue2 = field.get(o2);

                if (!equal(fieldValue1, fieldValue2))
                {
                    throw new AssertionError("Field value mismatch: " + field + ", value1=" + fieldValue1 + ", value2="
                            + fieldValue2);
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException("Cannot copy field: " + field, e);
            }
        }
    }

    private HpbTestUtils()
    {
    }
}
