package com.erinors.hpb.tests.integration.case0006;

public class FinalFieldWithoutSetter
{
    private final int i;

    @SuppressWarnings("unused")
    private FinalFieldWithoutSetter()
    {
        i = 0;
    }

    public FinalFieldWithoutSetter(int i)
    {
        this.i = i;
    }

    public int getI()
    {
        return i;
    }
}
