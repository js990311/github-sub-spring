package com.githubsub.domain.crawler.service.crawling;

import com.githubsub.domain.crawler.dto.SourceCodeDto;
import com.githubsub.domain.crawler.service.crawling.extension.SourceCodeExtension;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GitSourceCodeCrawlerTest {
    @Autowired private GitSourceCodeCrawler codeCrawler;

    @Test
    void crawling() {
        SourceCodeDto crawling = codeCrawler.crawling("https://github.com/js990311/spring-shortener/blob/develop/src/main/java/com/toyproject/shortener/aop/MethodTimeCheckerAdvice.java");

        assertEquals(crawling.getExtension(), SourceCodeExtension.JAVA);
    }
}