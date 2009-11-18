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
