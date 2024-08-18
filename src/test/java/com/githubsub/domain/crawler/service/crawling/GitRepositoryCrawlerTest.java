package com.githubsub.domain.crawler.service.crawling;

import com.githubsub.domain.crawler.dto.GitRepositoryDto;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GitRepositoryCrawlerTest {
    @Autowired private WebDriver webDriver;

}