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

package com.erinors.hpb.server.handler;

import java.beans.PropertyDescriptor;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

/**
 * Hibernate handles embedded objects in a special way, this handler helps to achieve a more logical operation.<br/>
 * Let's suppose that an embedded property of a versioned entity is {@code null}. If the property is modified to an
 * empty embedded object (ie. the embedded object is not {@code null} but all of its properties are {@code null}) then:
 * <ul>
 * <li>the entity is marked dirty therefore its version number will be incremented</li>
 * <li>no database updates will be performed (except the version number)</li>
 * </ul>
 * The most practical solution would be to revert such changes made to an entity, but because of HHH-5911 it is
 * currently not possible.
 */
@Service
public class HibernateEmbeddedObjectHandler extends JavaBeanHandler {
    public HibernateEmbeddedObjectHandler()
    {
        super(400);
    }
    
    @Override
    public Object clone(CloningContext context, Object object) {
        return null;
    }

    @Override
    public Object merge(MergingContext context, Object object) {
        if (object == null || !object.getClass().isAnnotationPresent(Embeddable.class)) {
            return null;
        }

        boolean empty = true;
        try {
            for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(object)) {
                String propertyName = pd.getName();

                if (!"class".equals(propertyName) && !pd.getReadMethod().isAnnotationPresent(Transient.class)) {
                    if (PropertyUtils.getSimpleProperty(object, propertyName) != null) {
                        empty = false;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while checking embedded object: " + object, e);
        }

        if (empty)
        {
            context.addProcessedObject(object, null);
            return ProcessedToNull;
        }
        else
        {
            return null;
        }
    }
}
