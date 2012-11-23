package com.erinors.hpb.shared.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.erinors.hpb.shared.impl.BeforeCloneTask;
import com.erinors.hpb.shared.impl.BeforeJpaFlushTask;
import com.erinors.hpb.shared.impl.BeforeMergeTask;
import com.erinors.hpb.shared.impl.DefaultBeforeCloneTask;
import com.erinors.hpb.shared.impl.DefaultBeforeJpaFlushTask;
import com.erinors.hpb.shared.impl.DefaultBeforeMergeTask;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoPersistentObjectManagement
{
    Class<? extends BeforeMergeTask> beforeMergeTaskClass() default DefaultBeforeMergeTask.class;

    boolean mergeArguments() default true;

    Class<? extends BeforeJpaFlushTask> beforeJpaFlushTaskClass() default DefaultBeforeJpaFlushTask.class;

    boolean jpaFlushBeforeClone() default true;

    Class<? extends BeforeCloneTask> beforeCloneTaskClass() default DefaultBeforeCloneTask.class;

    boolean cloneReturnValue() default true;
}
