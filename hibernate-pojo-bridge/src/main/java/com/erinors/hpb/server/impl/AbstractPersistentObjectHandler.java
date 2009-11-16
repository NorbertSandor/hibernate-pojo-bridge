/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

public abstract class AbstractPersistentObjectHandler implements PersistentObjectHandler
{
    private int order;

    @Override
    public int getOrder()
    {
        return order;
    }

    public void setOrder(int order)
    {
        this.order = order;
    }
}
