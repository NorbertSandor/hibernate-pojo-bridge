package com.erinors.hpb.shared.impl;

public class DefaultBeforeJpaFlushTask implements BeforeJpaFlushTask
{
    @Override
    public Object onBeforeJpaFlush(Object object)
    {
        return object;
    }
}
