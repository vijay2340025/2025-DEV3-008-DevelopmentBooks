package com.kata.DevelopmentBooks.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.kata.DevelopmentBooks.controller..*.*(..))")
    public Object aroundControllerLogging(ProceedingJoinPoint joinPoint) {
        try {
            long start = System.currentTimeMillis();

            String methodName = joinPoint.getSignature().toShortString();
            Object[] args = joinPoint.getArgs();
            log.debug("Calling method: {} with args: {}", methodName, args);

            Object result = joinPoint.proceed();

            long end = System.currentTimeMillis();
            log.debug("Method {} returned: {} in {}ms", methodName, result, (end - start));

            return result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
