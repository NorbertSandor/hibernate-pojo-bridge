/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration.case0000;

public class Bean
{
    private int value;

    public Bean()
    {
    }

    public Bean(int value)
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
