package com.erinors.hpb.shared.impl;

public class DefaultBeforeCloneTask implements BeforeCloneTask
{
    @Override
    public Object onBeforeClone(Object object)
    {
        return object;
    }
}
