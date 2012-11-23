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

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Norbert Sándor
 */
public abstract class AbstractContext implements Context
{
    private final Map<Object, Object> processedObjects = new IdentityHashMap<Object, Object>();

    private final List<? extends PersistentObjectHandler> handlers;

    protected AbstractContext(List<? extends PersistentObjectHandler> handlers)
    {
        this.handlers = handlers;
    }

    @Override
    public void addProcessedObject(Object source, Object processed)
    {
        if (processedObjects.containsKey(source))
        {
            throw new IllegalStateException("Object already processed: " + source);
        }

        processedObjects.put(source, processed);
    }

    @Override
    public Object getProcessedObject(Object source)
    {
        return processedObjects.get(source);
    }

    @Override
    public boolean isProcessed(Object source)
    {
        return processedObjects.containsKey(source);
    }

    protected List<? extends PersistentObjectHandler> getHandlers()
    {
        return handlers;
    }
}
