/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
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
