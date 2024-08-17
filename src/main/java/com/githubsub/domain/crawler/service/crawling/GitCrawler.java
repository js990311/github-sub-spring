package com.githubsub.domain.crawler.service.crawling;

import com.githubsub.domain.crawler.service.crawling.extension.SourceCodeExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitCrawler implements Crawler<Object> {
    private static Pattern extensionPattern = Pattern.compile("r\"\\.([a-zA-Z0-9]+)$\"");
    private static Pattern treePattern = Pattern.compile("/tree");
    private static Map<String, String> extensionMap;

    private Queue<String> sourceCodes;
    private Queue<String> queue;
    private RemoteWebDriver webDriver;
    private Set<String> linkSet;

    public GitCrawler(RemoteWebDriver webDriver) {
        this.queue = new LinkedList<>();
        this.sourceCodes = new LinkedList<>();
        this.webDriver = webDriver;
        linkSet = new HashSet<>();
    }

    @Override
    public Object crawling(String url) {
        queue.add(url);
        while (!queue.isEmpty()){
            String front = queue.poll();
            search(front);
        }
        return null;
    }

    public void search(String url){
        webDriver.get(url);
        WebElement table = webDriver.findElement(By.tagName("table"));
        List<WebElement> links = table.findElements(By.tagName("a"));
        for(WebElement link : links){
            String href = link.getAttribute("href");
            if(href == null || linkSet.contains(href)){
                continue;
            }
            linkSet.add(href);
            Matcher extensionMatcher = extensionPattern.matcher(href);
            if(extensionMatcher.find()){
                String extesion = extensionMatcher.group();
                if(SourceCodeExtension.fromExtension(extesion)){
                    sourceCodes.add(href);
                }
            }else {
                Matcher treeMatcher = treePattern.matcher(href);
                if(treeMatcher.find()){
                    this.queue.add(href);
                }
            }
        }
    }
}
