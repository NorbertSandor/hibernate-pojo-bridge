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

package com.erinors.hpb.tests.integration.case0000;

public class Bean
{
    private int value;

    protected Bean()
    {
    }

    public Bean(int value)
    {
        setValue(value);
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Bean))
        {
            return false;
        }

        Bean other = (Bean) obj;
        return getValue() == other.getValue();
    }

    @Override
    public int hashCode()
    {
        return getValue();
    }
}
