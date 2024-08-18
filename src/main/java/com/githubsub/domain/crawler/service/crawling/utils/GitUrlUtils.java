package com.githubsub.domain.crawler.service.crawling.utils;

import com.githubsub.domain.crawler.service.crawling.extension.SourceCodeExtension;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GitUrlUtils {
    private final Pattern fileNamePattern = Pattern.compile("([a-zA-Z0-9]+)\\.([a-zA-Z0-9]+)$");
    private static Pattern treePattern = Pattern.compile("/tree");

    public String findSourceName(String url){
        Matcher matcher = fileNamePattern.matcher(url);
        if(matcher.find()){
            return matcher.group();
        }else {
            return null;
        }
    }

    public SourceCodeExtension findExtension(String url){
        Matcher matcher = fileNamePattern.matcher(url);
        if(matcher.find()){
            String extension = matcher.group(2);
            return SourceCodeExtension.fromExtension(extension);
        }else {
            return null;
        }
    }

    public boolean isTree(String url){
        Matcher matcher = treePattern.matcher(url);
        return matcher.find();
    }
}
