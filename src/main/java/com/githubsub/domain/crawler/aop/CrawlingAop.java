package com.githubsub.domain.crawler.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CrawlingAop {

    @Around("@annotation(com.githubsub.domain.crawler.aop.annotation.RetryCrawling)")
    public Object retryCrawling(ProceedingJoinPoint joinPoint) throws InterruptedException {
        int retry = 0;
        while (retry < 5){
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                Thread.sleep(500);
                retry++;
                if(retry>=5){
                    break;
                }
            }
        }
        throw new RuntimeException();
    }

    @Around("@annotation(com.githubsub.domain.crawler.aop.annotation.CreateWebDriver)")
    public Object createWebDriver(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
