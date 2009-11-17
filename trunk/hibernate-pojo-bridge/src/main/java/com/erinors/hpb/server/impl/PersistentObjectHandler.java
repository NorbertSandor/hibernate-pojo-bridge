/*
 * Copyright (c) Erinors 2007-2010. All rights reserved.
 */
package com.erinors.hpb.server.impl;

import org.springframework.core.Ordered;

/**
 * @author Norbert Sándor
 */
public interface PersistentObjectHandler extends Ordered
{
    Object clone(CloningContext context, Object object);

    Object merge(MergingContext context, Object object);
}
