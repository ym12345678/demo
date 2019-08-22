package com.domain.schedulerConfig;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author: LJ
 * @create: 2018-11-16
 **/
public class SchedulerCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {


        boolean enable = Boolean.valueOf(conditionContext.getEnvironment().getProperty("scheduling.enabled"));
        return enable;
    }
}
