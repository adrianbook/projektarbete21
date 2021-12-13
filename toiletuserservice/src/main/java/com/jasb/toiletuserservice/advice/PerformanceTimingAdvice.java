package com.jasb.toiletuserservice.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;

@Aspect
@Component
public class PerformanceTimingAdvice {
    @Around(value = "execution(* com.jasb.toiletuserservice.*.*.*(..))")
    public Object performTimingMeasurement (ProceedingJoinPoint method) throws Throwable {
        long startTime =System.nanoTime();
        try {
            Object value= method.proceed();
            return value;
        }finally {
            long endTime= System.nanoTime();
            long timeTaken = endTime - startTime;
            System.out.println("The method " +
                    method.getSignature().getName() + " took " + (double)timeTaken
                    /1000000 + " ms");
        }
    }
}