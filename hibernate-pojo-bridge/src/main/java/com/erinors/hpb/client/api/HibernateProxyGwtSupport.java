package com.erinors.hpb.client.api;

/**
 * @author Norbert Sándor
 */
public interface HibernateProxyGwtSupport
{
    boolean isUninitializedHibernateProxy();

    void setUninitializedHibernateProxy(boolean value);

    // FIXME remove this property and store the proxy id in the id property of the object
    Object getUninitializedHibernateProxyId();

    void setUninitializedHibernateProxyId(Object id);
}
// FIXME Gwt helyett Pojo a névben
