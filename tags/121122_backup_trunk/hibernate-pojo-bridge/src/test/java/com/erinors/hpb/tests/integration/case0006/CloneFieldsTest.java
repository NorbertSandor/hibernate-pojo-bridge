package com.erinors.hpb.tests.integration.case0006;

import junit.framework.Assert;

import org.junit.Test;

import com.erinors.hpb.tests.integration.HpbIntegrationTestCase;

public class CloneFieldsTest extends HpbIntegrationTestCase
{
    @Test
    public void testFieldWithoutSetter()
    {
        FieldWithoutSetter o = new FieldWithoutSetter(1);
        FieldWithoutSetter clone = getPersistentObjectManager().clone(o);
        Assert.assertEquals(o.getI(), clone.getI());
    }

    @Test
    public void testFinalFieldWithoutSetter()
    {
        FinalFieldWithoutSetter o = new FinalFieldWithoutSetter(1);
        FinalFieldWithoutSetter clone = getPersistentObjectManager().clone(o);
        Assert.assertEquals(o.getI(), clone.getI());
    }

    @Test
    public void testSameFieldNameInSuperclass()
    {
        A o = new A();
        o.setI(1);
        o.setSuperI(2);

        A clone = getPersistentObjectManager().clone(o);
        Assert.assertEquals(o.getI(), clone.getI());
        Assert.assertEquals(o.getSuperI(), clone.getSuperI());
    }

    @Test
    public void testWithNestedClass()
    {
        WithNestedClass o = new WithNestedClass();
        WithNestedClass clone = getPersistentObjectManager().clone(o);
        Assert.assertEquals(o.getInner().getI(), clone.getInner().getI());
        Assert.assertEquals(clone.getInner(), clone.getInner().getMe());
    }

    @Test(expected = RuntimeException.class)
    public void testWithInnerClass()
    {
        WithInnerClass o = new WithInnerClass();
        getPersistentObjectManager().clone(o);
    }

    @Test
    public void testMultipleReferences()
    {
        WithMultipleReferences o = new WithMultipleReferences();
        A a = new A();
        o.setA1(a);
        o.setA2(a);

        WithMultipleReferences clone = getPersistentObjectManager().clone(o);
        Assert.assertEquals(clone.getA1(), clone.getA2());
    }
}
