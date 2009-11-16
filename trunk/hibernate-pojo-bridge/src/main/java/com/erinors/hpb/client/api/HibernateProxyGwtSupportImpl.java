package com.erinors.hpb.client.api;

public class HibernateProxyGwtSupportImpl implements HibernateProxyGwtSupport
{
    private Object uninitializedHibernateProxyId;

    private boolean uninitializedHibernateProxy;

    @Override
    public Object getUninitializedHibernateProxyId()
    {
        return uninitializedHibernateProxyId;
    }

    @Override
    public boolean isUninitializedHibernateProxy()
    {
        return uninitializedHibernateProxy;
    }

    @Override
    public void setUninitializedHibernateProxy(boolean value)
    {
        this.uninitializedHibernateProxy = value;
    }

    @Override
    public void setUninitializedHibernateProxyId(Object id)
    {
        this.uninitializedHibernateProxyId = id;
    }
}
//FIXME Gwt helyett Pojo a névben
