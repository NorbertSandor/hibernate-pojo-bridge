/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration.case0003;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.erinors.hpb.tests.integration.BaseEntity;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Parent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Referenced referenced;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Referenced getReferenced()
    {
        return referenced;
    }

    public void setReferenced(Referenced referenced)
    {
        this.referenced = referenced;
    }
}
