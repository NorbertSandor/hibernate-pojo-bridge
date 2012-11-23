package com.erinors.hpb.shared.impl;

import com.google.common.base.Function;

public class DefaultBeforeJpaFlushCallback implements Function<Object, Object>
{
    @Override
    public Object apply(Object input)
    {
        return input;
    }
}
