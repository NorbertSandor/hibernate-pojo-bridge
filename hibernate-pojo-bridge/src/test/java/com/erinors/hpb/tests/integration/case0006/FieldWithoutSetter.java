package com.erinors.hpb.tests.integration.case0006;

public class FieldWithoutSetter
{
    private int i;

    @SuppressWarnings("unused")
    private FieldWithoutSetter()
    {
    }

    public FieldWithoutSetter(int i)
    {
        this.i = i;
    }

    public int getI()
    {
        return i;
    }
}
