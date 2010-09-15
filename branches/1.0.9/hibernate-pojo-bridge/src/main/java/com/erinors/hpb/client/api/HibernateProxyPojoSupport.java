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
package com.erinors.hpb.client.api;

/**
 * @author Norbert Sándor
 */
public interface HibernateProxyPojoSupport
{
    boolean isUninitializedHibernateProxy();

    void setUninitializedHibernateProxy(boolean value);

    // TODO remove this property and store the proxy id in the id property of the object
    Object getUninitializedHibernateProxyId();

    void setUninitializedHibernateProxyId(Object id);
}
