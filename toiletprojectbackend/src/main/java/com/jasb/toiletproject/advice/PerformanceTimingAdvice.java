package com.jasb.toiletproject.advice;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
/**
 * A class containing aspect advice for development purposes
 * Written by JASB
 */
@Aspect
@Component
@Slf4j
public class PerformanceTimingAdvice {

    /**
     * A method that measures how long a method takes and logs to debug
     * @param method
     * @return object to pass on
     * @throws Throwable
     */
    @Around(value = "execution(* com.jasb.toiletproject.*.*.*(..))")
    public Object performTimingMeasurement (ProceedingJoinPoint method) throws Throwable {
        long startTime =System.nanoTime();
        try {
            Object value= method.proceed();
            return value;
        }finally {
            long endTime= System.nanoTime();
            long timeTaken = endTime - startTime;
            log.debug("The method " +
                    method.getSignature().getName() + " took " + (double)timeTaken
                    /1000000 + " ms");
        }
    }
}