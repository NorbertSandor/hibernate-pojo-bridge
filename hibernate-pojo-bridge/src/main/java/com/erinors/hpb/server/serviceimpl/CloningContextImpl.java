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

package com.erinors.hpb.server.serviceimpl;

import java.util.List;

import com.erinors.hpb.server.handler.AbstractContext;
import com.erinors.hpb.server.handler.CloningContext;
import com.erinors.hpb.server.handler.PersistentObjectHandler;

/**
 * @author Norbert Sándor
 */
public class CloningContextImpl extends AbstractContext implements CloningContext
{
    protected CloningContextImpl(List<? extends PersistentObjectHandler> handlers)
    {
        super(handlers);
    }

    @Override
    public Object clone(Object object)
    {
        if (object == null)
        {
            return null;
        }

        if (isProcessed(object))
        {
            return getProcessedObject(object);
        }
        else
        {
            Object cloned = null;
            for (PersistentObjectHandler handler : getHandlers())
            {
                cloned = handler.clone(this, object);

                // TODO handle PersistentObjectHandler.ProcessedToNull, see MergingContextImpl for implementation

                if (cloned != null)
                {
                    return cloned;
                }
            }

            throw new RuntimeException("Cannot clone object of unsupported type: " + object);
        }
    }
}
