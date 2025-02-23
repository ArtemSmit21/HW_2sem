package org.example.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* org.example.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Название метода: {}", joinPoint.getSignature().getName());
    }

    @Around("execution(* org.example.controller.*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = Instant.now().toEpochMilli();
        Object result = joinPoint.proceed();
        long duration = Instant.now().toEpochMilli() - startTime;
        log.info("Метод {} выполнен за {} мс", joinPoint.getSignature().getName(), duration);
        return result;
    }
}
