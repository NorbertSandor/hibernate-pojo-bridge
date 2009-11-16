/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration.case0002;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.IndexColumn;

import com.erinors.hpb.tests.integration.BaseEntity;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Parent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private List<Child> children;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexColumn(name = "childIndex")
    public List<Child> getChildren()
    {
        if (children == null)
        {
            children = new LinkedList<Child>();
        }

        return children;
    }

    public void setChildren(List<Child> children)
    {
        this.children = children;
    }
}
