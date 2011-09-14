package com.erinors.hpbexample.client.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.erinors.hpb.client.api.HibernateProxyPojoSupportImpl;

@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity extends HibernateProxyPojoSupportImpl implements Serializable
{
    private long id;

    private int version = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
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
}
