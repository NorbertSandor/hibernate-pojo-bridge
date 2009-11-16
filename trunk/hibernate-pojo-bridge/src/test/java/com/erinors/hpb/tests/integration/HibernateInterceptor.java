/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration;

import org.hibernate.EmptyInterceptor;

public class HibernateInterceptor extends EmptyInterceptor
{
    private static final long serialVersionUID = 1L;

    @Override
    public Boolean isTransient(Object entity)
    {
	if (entity instanceof BaseEntity)
	{
	    return ((BaseEntity) entity).isTransient();
	}
	else
	{
	    return null;
	}
    }
}
