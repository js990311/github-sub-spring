package com.githubsub.domain.crawler.service.crawling;

import com.githubsub.domain.crawler.dto.GitRepositoryDto;
import com.githubsub.domain.crawler.dto.SourceCodeDto;
import com.githubsub.domain.crawler.service.crawling.extension.SourceCodeExtension;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class GitRepositoryCrawlerTest {
    @Autowired private GitRepositoryCrawler repositoryCrawler;

    @Test
    void crawling() {
        GitRepositoryDto crawling = repositoryCrawler.crawling("https://github.com/js990311/spring-shortener");
        assertNotEquals(0, crawling.getCodes().size());
    }

}