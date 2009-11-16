/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration.case0004;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.erinors.hpb.tests.integration.BaseEntity;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Parent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Set<Child> children;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Child> getChildren()
    {
	if (children == null)
	{
	    children = new HashSet<Child>();
	}

	return children;
    }

    public void setChildren(Set<Child> children)
    {
	this.children = children;
    }
}
