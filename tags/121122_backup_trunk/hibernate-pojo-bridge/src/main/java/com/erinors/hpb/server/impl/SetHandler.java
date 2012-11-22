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

import java.util.HashSet;
import java.util.Set;

import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.collection.spi.PersistentCollection;

import com.erinors.hpb.client.impl.UninitializedPersistentSet;

/**
 * @author Norbert Sándor
 */
public class SetHandler extends AbstractPersistentObjectHandler
{
    @Override
    public Object clone(CloningContext context, Object object)
    {
        if (!(object instanceof Set))
        {
            return null;
        }

        Set<?> source = (Set<?>) object;
        Set<?> result;

        if (source instanceof PersistentSet)
        {
            if (((PersistentSet) source).wasInitialized())
            {
                com.erinors.hpb.client.impl.PersistentSet<Object> set = new com.erinors.hpb.client.impl.PersistentSet<Object>(
                        source.size());
                context.addProcessedObject(object, set);

                for (Object element : source)
                {
                    set.add(context.clone(element));
                }

                set.setDirty(((PersistentCollection) source).isDirty());

                result = set;
            }
            else
            {
                result = new UninitializedPersistentSet<Object>();
                context.addProcessedObject(object, result);
            }
        }
        else
        {
            Set<Object> set = new HashSet<Object>(source.size());
            context.addProcessedObject(object, set);

            for (Object element : source)
            {
                set.add(context.clone(element));
            }

            result = set;
        }

        return result;
    }

    @Override
    public Object merge(MergingContext context, Object object)
    {
        if (!(object instanceof Set))
        {
            return null;
        }

        Set<?> source = (Set<?>) object;
        Set<?> result;

        if (source instanceof UninitializedPersistentSet)
        {
            result = new PersistentSet(context.getSessionImplementor());
            context.addProcessedObject(object, result);
        }
        else if (source instanceof com.erinors.hpb.client.impl.PersistentSet)
        {
            PersistentSet set = new PersistentSet(context.getSessionImplementor(), new HashSet<Object>());
            context.addProcessedObject(object, set);

            for (Object element : source)
            {
                set.add(context.merge(element));
            }

            if (((com.erinors.hpb.client.impl.PersistentSet<?>) source).isDirty())
            {
                set.dirty();
            }
            else
            {
                set.clearDirty();
            }

            result = set;
        }
        else
        {
            Set<Object> set = new HashSet<Object>(source.size());
            context.addProcessedObject(object, set);

            for (Object element : source)
            {
                set.add(context.merge(element));
            }

            result = set;
        }

        return result;
    }

}
// TODO handle unmodifiable, synchronized, etc collections as well
