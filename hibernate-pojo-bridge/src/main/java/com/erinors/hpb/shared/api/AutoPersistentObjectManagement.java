package com.erinors.hpb.shared.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.erinors.hpb.shared.impl.DefaultBeforeCloneCallback;
import com.erinors.hpb.shared.impl.DefaultBeforeJpaFlushCallback;
import com.erinors.hpb.shared.impl.DefaultBeforeMergeCallback;
import com.google.common.base.Function;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoPersistentObjectManagement
{
    Class<? extends Function<Object[], Object[]>> beforeMergeCallbackClass() default DefaultBeforeMergeCallback.class;

    boolean mergeArguments() default true;

    Class<? extends Function<Object, Object>> beforeJpaFlushCallbackClass() default DefaultBeforeJpaFlushCallback.class;

    boolean jpaFlushBeforeClone() default true;

    Class<? extends Function<Object, Object>> beforeCloneCallbackClass() default DefaultBeforeCloneCallback.class;

    boolean cloneReturnValue() default true;
}
