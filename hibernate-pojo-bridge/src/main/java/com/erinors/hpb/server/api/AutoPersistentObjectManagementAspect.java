package com.erinors.hpb.server.api;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.erinors.hpb.shared.api.AutoPersistentObjectManagement;
import com.erinors.hpb.shared.impl.DefaultBeforeCloneCallback;
import com.erinors.hpb.shared.impl.DefaultBeforeJpaFlushCallback;
import com.erinors.hpb.shared.impl.DefaultBeforeMergeCallback;
import com.google.common.base.Function;

@Aspect
@Component
public class AutoPersistentObjectManagementAspect
{
    @Autowired
    private PersistentObjectManager persistentObjectManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Around(value = "@annotation(autoPersistentObjectManagement)", argNames = "autoPersistentObjectManagement")
    public Object around(ProceedingJoinPoint joinPoint, AutoPersistentObjectManagement autoPersistentObjectManagement)
            throws Throwable
    {
        Object[] arguments = joinPoint.getArgs();

        if (autoPersistentObjectManagement.mergeArguments())
        {
            Class<? extends Function<Object[], Object[]>> beforeMergeCallbackClass = autoPersistentObjectManagement
                    .beforeMergeCallbackClass();
            if (!DefaultBeforeMergeCallback.class.equals(beforeMergeCallbackClass))
            {
                arguments = createArrayCallback(beforeMergeCallbackClass).apply(arguments);
            }

            arguments = persistentObjectManager.merge(arguments);
        }

        Object returnValue = joinPoint.proceed(arguments);

        if (autoPersistentObjectManagement.jpaFlushBeforeClone())
        {
            Class<? extends Function<Object, Object>> beforeJpaFlushCallbackClass = autoPersistentObjectManagement
                    .beforeJpaFlushCallbackClass();
            if (!DefaultBeforeJpaFlushCallback.class.equals(beforeJpaFlushCallbackClass))
            {
                returnValue = createObjectCallback(beforeJpaFlushCallbackClass).apply(returnValue);
            }

            entityManager.flush();
        }

        if (autoPersistentObjectManagement.cloneReturnValue())
        {
            Class<? extends Function<Object, Object>> beforeCloneCallbackClass = autoPersistentObjectManagement
                    .beforeCloneCallbackClass();
            if (!DefaultBeforeCloneCallback.class.equals(beforeCloneCallbackClass))
            {
                returnValue = createObjectCallback(beforeCloneCallbackClass).apply(returnValue);
            }

            returnValue = persistentObjectManager.clone(returnValue);
        }

        return returnValue;
    }

    private Function<Object[], Object[]> createArrayCallback(Class<? extends Function<Object[], Object[]>> callbackClass)
    {
        try
        {
            return callbackClass.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Cannot instantiate callback: " + callbackClass, e);
        }
    }

    private Function<Object, Object> createObjectCallback(Class<? extends Function<Object, Object>> callbackClass)
    {
        try
        {
            return callbackClass.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Cannot instantiate callback: " + callbackClass, e);
        }
    }
}
