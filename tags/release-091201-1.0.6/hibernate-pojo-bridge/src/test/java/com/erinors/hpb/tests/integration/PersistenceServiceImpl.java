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

package com.erinors.hpb.tests.integration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

public class PersistenceServiceImpl implements PersistenceService
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void executeTransactionally(Runnable task)
    {
        task.run();
    }

    @Override
    public EntityManager getEntityManager()
    {
        return entityManager;
    }
}
