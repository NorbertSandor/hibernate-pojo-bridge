/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;

public class HibernateIdGenerator extends org.hibernate.id.SequenceGenerator
{
    @Override
    public void configure(Type type, Properties params, Dialect dialect) throws MappingException
    {
	String sequenceParameters;
	if (dialect instanceof Oracle10gDialect)
	{
	    sequenceParameters = "START WITH 1 INCREMENT BY 1 NOCACHE";
	}
	else
	{
	    sequenceParameters = "START WITH 1 INCREMENT BY 1";
	}

	params.setProperty(PARAMETERS, sequenceParameters);

	super.configure(type, params, dialect);
    }

    @Override
    public Serializable generate(SessionImplementor session, Object obj) throws HibernateException
    {
	Serializable id;

	if (obj instanceof BaseEntity)
	{
	    BaseEntity entity = (BaseEntity) obj;

	    if (entity.getId() == 0)
	    {
		id = super.generate(session, obj);
	    }
	    else
	    {
		id = entity.getId();
	    }
	}
	else
	{
	    id = super.generate(session, obj);
	}

	return id;
    }
}
