/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration.case0004;

import javax.persistence.Entity;

import com.erinors.hpb.tests.integration.BaseEntity;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Child extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private int value;

    public Child()
    {
    }

    public Child(int value)
    {
	setValue(value);
    }

    public int getValue()
    {
	return value;
    }

    public void setValue(int value)
    {
	this.value = value;
    }
}
