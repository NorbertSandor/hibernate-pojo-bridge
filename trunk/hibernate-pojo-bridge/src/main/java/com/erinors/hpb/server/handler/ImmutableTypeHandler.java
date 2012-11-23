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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

/**
 * @author Norbert Sándor
 */
@Service
public class ImmutableTypeHandler extends AbstractPersistentObjectHandler
{
    public ImmutableTypeHandler()
    {
        super(Ordered.HIGHEST_PRECEDENCE);
    }

    private static final Set<Class<?>> immutableTypeClasses = new HashSet<Class<?>>(Arrays.asList(new Class<?>[] {
            Boolean.class, Byte.class, Character.class, Double.class, Float.class, Integer.class, Long.class,
            Short.class, String.class, BigDecimal.class, BigInteger.class }));

    @Override
    public Object clone(CloningContext context, Object object)
    {
        return copy(object);
    }

    @Override
    public Object merge(MergingContext context, Object object)
    {
        return copy(object);
    }

    private Object copy(Object object)
    {
        if (immutableTypeClasses.contains(object.getClass()) || object instanceof Enum)
        {
            return object;
        }
        else
        {
            return null;
        }
    }
}
