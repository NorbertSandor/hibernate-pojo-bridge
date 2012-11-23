package com.erinors.hpb.shared.impl;

public interface BeforeJpaFlushTask
{
    Object onBeforeJpaFlush(Object object);
}
