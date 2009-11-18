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

package com.erinors.hpb.tests.integration;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;

public class HibernateIdGenerator extends org.hibernate.id.SequenceGenerator
{
    @Override
    public void configure(Type type, Properties params, Dialect dialect) throws MappingException
    {
        String sequenceParameters;
        if (dialect instanceof Oracle10gDialect)
        {
            sequenceParameters = "START WITH 1 INCREMENT BY 1 NOCACHE";
        }
        else
        {
            sequenceParameters = "START WITH 1 INCREMENT BY 1";
        }

        params.setProperty(PARAMETERS, sequenceParameters);

        super.configure(type, params, dialect);
    }

    @Override
    public Serializable generate(SessionImplementor session, Object obj) throws HibernateException
    {
        Serializable id;

        if (obj instanceof BaseEntity)
        {
            BaseEntity entity = (BaseEntity) obj;

            if (entity.getId() == 0)
            {
                id = super.generate(session, obj);
            }
            else
            {
                id = entity.getId();
            }
        }
        else
        {
            id = super.generate(session, obj);
        }

        return id;
    }
}
