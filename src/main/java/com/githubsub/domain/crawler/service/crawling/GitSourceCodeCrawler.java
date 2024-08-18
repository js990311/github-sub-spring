package com.githubsub.domain.crawler.service.crawling;

import com.githubsub.domain.crawler.dto.SourceCodeDto;
import com.githubsub.domain.crawler.service.crawling.driver.factory.WebDriverFactory;
import com.githubsub.domain.crawler.service.crawling.extension.SourceCodeExtension;
import com.githubsub.domain.crawler.service.crawling.utils.GitUrlUtils;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.regex.Matcher;

@RequiredArgsConstructor
@Component
public class GitSourceCodeCrawler implements Crawler<SourceCodeDto> {
    private final WebDriverFactory webDriverFactory;
    private final GitUrlUtils gitUrlUtils;

    @Override
    public SourceCodeDto crawling(String url) {
        WebDriver webDriver = webDriverFactory.getWebDriver();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
        WebElement textarea = webDriver.findElement(By.id("read-only-cursor-text-area"));
        // WebElement textarea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("read-only-cursor-text-area")));
        String code = textarea.getText();
        String name = gitUrlUtils.findSourceName(url);
        SourceCodeExtension extension = gitUrlUtils.findExtension(url);

        return SourceCodeDto.builder()
                .name(name)
                .url(url)
                .content(code)
                .extension(extension)
                .build();
    }
}
