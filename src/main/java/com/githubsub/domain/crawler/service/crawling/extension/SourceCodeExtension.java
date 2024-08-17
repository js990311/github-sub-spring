package com.githubsub.domain.crawler.service.crawling.extension;

import lombok.Getter;

@Getter
public enum SourceCodeExtension {
    PYTHON("py", "python"),
    JAVA("java", "java"),
    CPLUSPLUS("cpp", "c++"),
    JAVASCRIPT("js", "javascript"),
    HTML("html", "html"),
    CSS("css", "css");

    private final String extension;
    private final String language;
    SourceCodeExtension(String extension, String language) {
        this.extension = extension;
        this.language = language;
    }

    public static boolean fromExtension(String extension){
        for(SourceCodeExtension ex : values()){
            if(ex.getExtension().equals(extension)){
                return true;
            }
        }
        return false;
    }

}
