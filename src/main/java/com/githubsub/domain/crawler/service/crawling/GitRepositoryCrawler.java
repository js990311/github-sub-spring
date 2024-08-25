package com.githubsub.domain.crawler.service.crawling;

import com.githubsub.domain.crawler.aop.annotation.RetryCrawling;
import com.githubsub.domain.crawler.aop.annotation.UseWebDriver;
import com.githubsub.domain.crawler.dto.GitRepositoryDto;
import com.githubsub.domain.crawler.dto.SourceCodeDto;
import com.githubsub.domain.crawler.service.crawling.driver.factory.WebDriverFactory;
import com.githubsub.domain.crawler.service.crawling.extension.SourceCodeExtension;
import com.githubsub.domain.crawler.service.crawling.utils.GitUrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.N;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class GitRepositoryCrawler implements Crawler<GitRepositoryDto> {
    private final WebDriverFactory webDriverFactory;
    private final GitSourceCodeCrawler codeCrawler;
    private final GitUrlUtils gitUrlUtils;

    public GitRepositoryCrawler(WebDriverFactory webDriverFactory, GitSourceCodeCrawler codeCrawler, GitUrlUtils gitUrlUtils) {
        this.webDriverFactory = webDriverFactory;
        this.codeCrawler = codeCrawler;
        this.gitUrlUtils = gitUrlUtils;
    }

    @UseWebDriver
    @Override
    public GitRepositoryDto crawling(String url) {
        Queue<String > directories = new LinkedList<>();
        List<SourceCodeDto> sourceCodes = new ArrayList<>();
        List<String> codeUrls = new ArrayList<>();
        Set<String> isVisit = new HashSet<>();
        directories.add(url);

        while (!directories.isEmpty()){
            String  front = directories.poll();
            search(front,directories, codeUrls, isVisit);
        }

        for(String codeUrl : codeUrls){
            sourceCodes.add(codeCrawler.crawling(codeUrl));
        }

        return GitRepositoryDto.builder()
                .name(null)
                .url(url)
                .codes(sourceCodes)
                .build();
    }

    /**
     *
     * @param url
     * @param directories
     * @param codeUrls
     * @param isVisit
     * @return
     */
    private void search(String url, Queue<String> directories, List<String> codeUrls, Set<String> isVisit){
        WebDriver webDriver = webDriverFactory.getWebDriver();
        webDriver.get(url);
        List<WebElement> links = webDriver.findElements(By.className("react-directory-row-name-cell-large-screen"));
        for(WebElement link : links){
            try {
                String href = link.findElement(By.tagName("a")).getAttribute("href");

                // isVisit
                if(isVisit.contains(href)){
                    continue;
                }else {
                    isVisit.add(href);
                }

                // 코드인지 디렉토리인지 검사
                SourceCodeExtension extension = gitUrlUtils.findExtension(href);
                if(extension != null){
                    codeUrls.add(href);
                }else if(gitUrlUtils.isTree(href)){
                    directories.add(href);
                }
            }catch (Exception e){
                log.warn(e.getMessage());
            }
        }
    }

}
