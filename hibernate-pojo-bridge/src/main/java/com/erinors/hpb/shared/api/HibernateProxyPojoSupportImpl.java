/*
 * Copyright 2009 Norbert Sándor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.erinors.hpb.shared.api;

/**
 * @author Norbert Sándor
 */
public class HibernateProxyPojoSupportImpl implements HibernateProxyPojoSupport
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
