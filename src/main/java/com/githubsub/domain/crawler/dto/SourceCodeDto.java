package com.githubsub.domain.crawler.dto;

import com.githubsub.domain.crawler.service.crawling.extension.SourceCodeExtension;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SourceCodeDto {
    private String url;
    private String name;
    private String content;
    private SourceCodeExtension extension;

    public static SourceCodeDto.SourceCodeDtoBuilder builder(){
        return new SourceCodeDtoBuilder();
    }
}
