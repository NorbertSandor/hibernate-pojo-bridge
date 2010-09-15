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

import java.util.HashMap;
import java.util.Map;

import org.hibernate.collection.PersistentCollection;
import org.hibernate.collection.PersistentMap;

import com.erinors.hpb.client.impl.UninitializedPersistentMap;

/**
 * @author Norbert Sándor
 */
public class MapHandler extends AbstractPersistentObjectHandler
{
    @Override
    public Object clone(CloningContext context, Object object)
    {
        if (!(object instanceof Map))
        {
            return null;
        }

        Map<?, ?> source = (Map<?, ?>) object;
        Map<?, ?> result;

        if (source instanceof PersistentMap)
        {
            if (((PersistentMap) source).wasInitialized())
            {
                com.erinors.hpb.client.impl.PersistentMap<Object, Object> map = new com.erinors.hpb.client.impl.PersistentMap<Object, Object>(
                        source.size());
                context.addProcessedObject(object, map);

                for (Map.Entry<?, ?> entry : source.entrySet())
                {
                    map.put(context.clone(entry.getKey()), context.clone(entry.getValue()));
                }

                map.setDirty(((PersistentCollection) source).isDirty());

                result = map;
            }
            else
            {
                result = new UninitializedPersistentMap<Object, Object>();
                context.addProcessedObject(object, result);
            }
        }
        else
        {
            Map<Object, Object> map = new HashMap<Object, Object>(source.size());
            context.addProcessedObject(object, map);

            for (Map.Entry<?, ?> entry : source.entrySet())
            {
                map.put(context.clone(entry.getKey()), context.clone(entry.getValue()));
            }

            result = map;
        }

        return result;
    }

    @Override
    public Object merge(MergingContext context, Object object)
    {
        if (!(object instanceof Map))
        {
            return null;
        }

        Map<?, ?> source = (Map<?, ?>) object;
        Map<?, ?> result;

        if (source instanceof UninitializedPersistentMap)
        {
            result = new PersistentMap(context.getSessionImplementor());
            context.addProcessedObject(object, result);
        }
        else if (source instanceof com.erinors.hpb.client.impl.PersistentMap)
        {
            PersistentMap map = new PersistentMap(context.getSessionImplementor(), new HashMap<Object, Object>());
            context.addProcessedObject(object, map);

            for (Map.Entry<?, ?> entry : source.entrySet())
            {
                map.put(context.merge(entry.getKey()), context.merge(entry.getValue()));
            }

            if (((com.erinors.hpb.client.impl.PersistentMap<?, ?>) source).isDirty())
            {
                map.dirty();
            }
            else
            {
                map.clearDirty();
            }

            result = map;
        }
        else
        {
            Map<Object, Object> map = new HashMap<Object, Object>(source.size());
            context.addProcessedObject(object, map);

            for (Map.Entry<?, ?> entry : source.entrySet())
            {
                map.put(context.merge(entry.getKey()), context.merge(entry.getValue()));
            }

            result = map;
        }

        return result;
    }
}
