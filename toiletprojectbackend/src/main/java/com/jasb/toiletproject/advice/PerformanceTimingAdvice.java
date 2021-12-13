package com.jasb.toiletproject.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;

@Aspect
@Component
public class PerformanceTimingAdvice {
    @Around(value = "execution(* com.jasb.toiletproject.*.*.*(..))")
    public Object performTimingMeasurement (ProceedingJoinPoint method) throws Throwable {
        //before
        long startTime =System.nanoTime();
        try {
            //proceed to target
            Object value= method.proceed();
            return value;
        }finally {
            //after
            long endTime= System.nanoTime();
            long timeTaken = endTime - startTime;
            System.out.println("The method " +
                    method.getSignature().getName() + " took " + (double)timeTaken
                    /1000000 + " ms");
        }
    }
}