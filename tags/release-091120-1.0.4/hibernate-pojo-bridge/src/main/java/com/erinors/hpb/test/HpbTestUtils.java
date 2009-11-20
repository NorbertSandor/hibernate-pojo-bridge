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

import java.util.List;

import org.junit.Assert;

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

    private HpbTestUtils()
    {
    }
}
