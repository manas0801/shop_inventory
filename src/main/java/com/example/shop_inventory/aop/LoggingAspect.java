package com.example.shop_inventory.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AdviceName;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Aspect
@Component
public class LoggingAspect {


    @Around("@annotation(LogExecutionTime)")
    //@Around(" execution( * com.example.shop_inventory.service.impl.productInfoServiceImpl.*(..) ) " )
    public Object LogExecution (ProceedingJoinPoint pjp) throws Throwable{

        long start =System.currentTimeMillis();
        Object result = pjp.proceed();
        long duration  = System.currentTimeMillis() - start ;
        System.out.println(pjp.getSignature() + "execution time " + duration + "ms");
        return result;
    }


}
