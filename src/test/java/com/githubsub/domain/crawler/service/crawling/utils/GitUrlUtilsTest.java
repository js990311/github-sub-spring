package com.githubsub.domain.crawler.service.crawling.utils;

import com.githubsub.domain.crawler.service.crawling.extension.SourceCodeExtension;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GitUrlUtilsTest {
    @Autowired private GitUrlUtils gitUrlUtils;

    @Test
    void findSourceName() {
        String fileName = gitUrlUtils.findSourceName("https://github.com/js990311/spring-shortener/blob/develop/src/main/java/com/toyproject/shortener/aop/MethodTimeCheckerAdvice.java");
        assertEquals("MethodTimeCheckerAdvice.java", fileName);
    }

    @Test
    void findExtension() {
        SourceCodeExtension extension = gitUrlUtils.findExtension("https://github.com/js990311/spring-shortener/blob/develop/src/main/java/com/toyproject/shortener/aop/MethodTimeCheckerAdvice.java");
        assertEquals(SourceCodeExtension.JAVA, extension);
    }

    @Test
    void isTree() {
        boolean isTree = gitUrlUtils.isTree("https://github.com/js990311/spring-shortener/tree/develop/src/main");
        assertEquals(true, isTree);
    }
}