/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import com.erinors.hpb.client.api.HibernateProxyGwtSupportImpl;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BaseEntity extends HibernateProxyGwtSupportImpl implements Serializable
{
    private long id;

    private int version = -1;

    @Id
    @GeneratedValue(generator = "EntitySequence")
    @GenericGenerator(name = "EntitySequence", strategy = "com.erinors.hpb.tests.integration.HibernateIdGenerator")
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public BaseEntity presetId(long id)
    {
        setId(id);
        return this;
    }

    @Version
    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof BaseEntity))
        {
            return false;
        }

        BaseEntity other = (BaseEntity) obj;

        if (getId() == 0 || other.getId() == 0)
        {
            throw new IllegalStateException("ID should be assigned for both entities before calling equals()");
        }

        return getId() == other.getId();
    }

    @Override
    public int hashCode()
    {
        if (getId() == 0)
        {
            throw new IllegalStateException("ID should be assigned before calling hashCode()");
        }

        return new Long(getId()).hashCode();
    }

    @Override
    public String toString()
    {
        return getClass().getName() + "-" + getId();
    }

    @Transient
    public boolean isTransient()
    {
        return getVersion() < 0;
    }

    @Transient
    public boolean isPersistent()
    {
        return !isTransient();
    }

    @PrePersist
    public void prePersist()
    {
        assert getVersion() == -1;
        setVersion(0);
    }
}
