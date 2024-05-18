package com.example.LibraryManagementSystem.Helpers;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.example.LibraryManagementSystem.Controllers.*Controller.*(..))")
    public void controllerLayerMethods() {}

    @Around("controllerLayerMethods()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        logger.info("Method {} of class {} executed in {} ms", joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getSimpleName(), endTime - startTime);

        return result;
    }

    @AfterThrowing(pointcut = "controllerLayerMethods()", throwing = "e")
    public void logException(JoinPoint joinPoint, Exception e) {
        logger.error("An exception occurred in method {} of class {}: {}", joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getSimpleName(), e.getMessage());
    }
}