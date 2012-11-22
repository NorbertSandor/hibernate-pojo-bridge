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

package com.erinors.hpb.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.collection.internal.PersistentList;
import org.hibernate.collection.spi.PersistentCollection;

import com.erinors.hpb.client.impl.UninitializedPersistentList;

/**
 * @author Norbert Sándor
 */
public class ListHandler extends AbstractPersistentObjectHandler
{
    @Override
    public Object clone(CloningContext context, Object object)
    {
        if (!(object instanceof List))
        {
            return null;
        }

        List<?> source = (List<?>) object;
        List<?> result;

        if (source instanceof PersistentList)
        {
            if (((PersistentList) source).wasInitialized())
            {
                com.erinors.hpb.client.impl.PersistentList<Object> list = new com.erinors.hpb.client.impl.PersistentList<Object>(
                        source.size());
                context.addProcessedObject(object, list);

                for (Object element : source)
                {
                    list.add(context.clone(element));
                }

                list.setDirty(((PersistentCollection) source).isDirty());

                result = list;
            }
            else
            {
                result = new UninitializedPersistentList<Object>();
                context.addProcessedObject(object, result);
            }
        }
        else
        {
            List<Object> list = new ArrayList<Object>(source.size());
            context.addProcessedObject(object, list);

            for (Object element : source)
            {
                list.add(context.clone(element));
            }

            result = list;
        }

        return result;
    }

    @Override
    public Object merge(MergingContext context, Object object)
    {
        if (!(object instanceof List))
        {
            return null;
        }

        List<?> source = (List<?>) object;
        List<?> result;

        if (source instanceof UninitializedPersistentList)
        {
            result = new PersistentList(context.getSessionImplementor());
            context.addProcessedObject(object, result);
        }
        else if (source instanceof com.erinors.hpb.client.impl.PersistentList)
        {
            PersistentList list = new PersistentList(context.getSessionImplementor(), new ArrayList<Object>());
            context.addProcessedObject(object, list);

            for (Object element : source)
            {
                list.add(context.merge(element));
            }

            if (((com.erinors.hpb.client.impl.PersistentList<?>) source).isDirty())
            {
                list.dirty();
            }
            else
            {
                list.clearDirty();
            }

            result = list;
        }
        else
        {
            List<Object> list = new ArrayList<Object>(source.size());
            context.addProcessedObject(object, list);

            for (Object element : source)
            {
                list.add(context.merge(element));
            }

            result = list;
        }

        return result;
    }
}
