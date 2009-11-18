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

import java.util.List;

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

        Object alreadyCloned = getProcessedObject(object);

        if (alreadyCloned != null)
        {
            return alreadyCloned;
        }
        else
        {
            Object cloned = null;
            for (PersistentObjectHandler handler : getHandlers())
            {
                cloned = handler.clone(this, object);
                if (cloned != null)
                {
                    return cloned;
                }
            }

            throw new RuntimeException("Cannot clone object of unsupported type: " + object);
        }
    }
}
