package com.erinors.hpb.server.serviceimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.erinors.hpb.server.service.PersistentObjectManager;
import com.erinors.hpb.server.util.ClassUtils;
import com.erinors.hpb.shared.api.AutoPersistentObjectManagement;
import com.erinors.hpb.shared.impl.BeforeCloneTask;
import com.erinors.hpb.shared.impl.BeforeJpaFlushTask;
import com.erinors.hpb.shared.impl.BeforeMergeTask;
import com.erinors.hpb.shared.impl.DefaultBeforeCloneTask;
import com.erinors.hpb.shared.impl.DefaultBeforeJpaFlushTask;
import com.erinors.hpb.shared.impl.DefaultBeforeMergeTask;

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
            Class<? extends BeforeMergeTask> beforeMergeTaskClass = autoPersistentObjectManagement
                    .beforeMergeTaskClass();
            if (!DefaultBeforeMergeTask.class.equals(beforeMergeTaskClass))
            {
                arguments = ClassUtils.newInstance(beforeMergeTaskClass).onBeforeMerge(arguments);
            }

            arguments = persistentObjectManager.merge(arguments);
        }

        Object returnValue = joinPoint.proceed(arguments);

        if (autoPersistentObjectManagement.jpaFlushBeforeClone())
        {
            Class<? extends BeforeJpaFlushTask> beforeJpaFlushTaskClass = autoPersistentObjectManagement
                    .beforeJpaFlushTaskClass();
            if (!DefaultBeforeJpaFlushTask.class.equals(beforeJpaFlushTaskClass))
            {
                returnValue = ClassUtils.newInstance(beforeJpaFlushTaskClass).onBeforeJpaFlush(returnValue);
            }

            entityManager.flush();
        }

        if (autoPersistentObjectManagement.cloneReturnValue())
        {
            Class<? extends BeforeCloneTask> beforeCloneTaskClass = autoPersistentObjectManagement
                    .beforeCloneTaskClass();
            if (!DefaultBeforeCloneTask.class.equals(beforeCloneTaskClass))
            {
                returnValue = ClassUtils.newInstance(beforeCloneTaskClass).onBeforeClone(returnValue);
            }

            returnValue = persistentObjectManager.clone(returnValue);
        }

        return returnValue;
    }
}
