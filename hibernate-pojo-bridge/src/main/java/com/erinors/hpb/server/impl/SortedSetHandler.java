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

import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.collection.internal.PersistentSortedSet;
import org.springframework.stereotype.Service;

import com.erinors.hpb.shared.impl.UninitializedPersistentSortedSet;

/**
 * @author Norbert Sándor
 */
@Service
public class SortedSetHandler extends AbstractPersistentObjectHandler
{
    public SortedSetHandler()
    {
        super(200);
    }

    @Override
    public Object clone(CloningContext context, Object object)
    {
        if (!(object instanceof SortedSet))
        {
            return null;
        }

        SortedSet<Object> source = (SortedSet<Object>) object;
        SortedSet<Object> result;

        if (source instanceof PersistentSortedSet && !((PersistentSortedSet) source).wasInitialized())
        {
            result = new UninitializedPersistentSortedSet<Object>(source.comparator());
            context.addProcessedObject(object, result);
        }
        else
        {
            SortedSet<Object> set = new TreeSet<Object>(source.comparator());
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
        if (!(object instanceof SortedSet))
        {
            return null;
        }

        SortedSet<Object> source = (SortedSet<Object>) object;
        SortedSet<Object> result;

        if (source instanceof UninitializedPersistentSortedSet)
        {
            result = new PersistentSortedSet(context.getSessionImplementor(), source);
            context.addProcessedObject(object, result);
        }
        else
        {
            SortedSet<Object> set = new TreeSet<Object>(source.comparator());
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
