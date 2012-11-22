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

package com.erinors.hpb.tests.integration.case0005;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import com.erinors.hpb.tests.integration.BaseEntity;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Parent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private int intValue;

    private EmbeddableObject embedded;

    public int getIntValue()
    {
        return intValue;
    }

    public void setIntValue(int value)
    {
        this.intValue = value;
    }

    @Embedded
    public EmbeddableObject getEmbedded() {
        return embedded;
    }

    public void setEmbedded(EmbeddableObject embedded) {
        this.embedded = embedded;
    }
}