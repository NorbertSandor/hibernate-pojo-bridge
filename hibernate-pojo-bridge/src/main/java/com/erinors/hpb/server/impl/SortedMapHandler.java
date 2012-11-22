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

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.hibernate.collection.internal.PersistentSortedMap;

import com.erinors.hpb.client.impl.UninitializedPersistentSortedMap;

/**
 * @author Norbert Sándor
 */
public class SortedMapHandler extends AbstractPersistentObjectHandler
{
    @Override
    public Object clone(CloningContext context, Object object)
    {
        if (!(object instanceof SortedMap))
        {
            return null;
        }

        SortedMap<Object, Object> source = (SortedMap<Object, Object>) object;
        SortedMap<Object, Object> result;

        if (source instanceof PersistentSortedMap && !((PersistentSortedMap) source).wasInitialized())
        {
            result = new UninitializedPersistentSortedMap<Object, Object>(source.comparator());
            context.addProcessedObject(object, result);
        }
        else
        {
            SortedMap<Object, Object> map = new TreeMap<Object, Object>(source.comparator());
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
        if (!(object instanceof SortedMap))
        {
            return null;
        }

        SortedMap<Object, Object> source = (SortedMap<Object, Object>) object;
        SortedMap<Object, Object> result;

        if (source instanceof UninitializedPersistentSortedMap)
        {
            result = new PersistentSortedMap(context.getSessionImplementor(), source);
            context.addProcessedObject(object, result);
        }
        else
        {
            SortedMap<Object, Object> map = new TreeMap<Object, Object>(source.comparator());
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
