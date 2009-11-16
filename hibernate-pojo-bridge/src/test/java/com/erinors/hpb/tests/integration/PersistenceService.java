/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.tests.integration;

import javax.persistence.EntityManager;

public interface PersistenceService
{
    void executeTransactionally(Runnable task);

    EntityManager getEntityManager();
}
