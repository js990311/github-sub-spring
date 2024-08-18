package com.githubsub.domain.crawler.aop;

import com.githubsub.domain.crawler.service.crawling.driver.factory.WebDriverFactory;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
public class CrawlingAop {

    private final WebDriverFactory webDriverFactory;

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

    @Around("@annotation(com.githubsub.domain.crawler.aop.annotation.UseWebDriver)")
    public Object createWebDriver(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean createWebDriver = false;
        try {
            createWebDriver = webDriverFactory.createDriver();
            return joinPoint.proceed();
        }catch (Exception e){
            throw e;
        }finally {
            if(createWebDriver){
                webDriverFactory.closeDriver();
            }
        }
    }
}
