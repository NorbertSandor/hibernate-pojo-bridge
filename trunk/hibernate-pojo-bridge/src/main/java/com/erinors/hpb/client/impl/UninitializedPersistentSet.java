/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.client.impl;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Norbert Sándor
 */
public class UninitializedPersistentSet<E> extends UninitializedPersistentCollection<E> implements Set<E>, Serializable
{
    private static final long serialVersionUID = 1L;
}
