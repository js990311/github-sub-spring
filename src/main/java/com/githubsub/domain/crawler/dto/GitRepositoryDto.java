package com.githubsub.domain.crawler.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GitRepositoryDto {
    private String name;
    private String url;
    private List<SourceCodeDto> codes;

    public static GitRepositoryDto.GitRepositoryDtoBuilder builder(){
        return new GitRepositoryDto.GitRepositoryDtoBuilder();
    }
}
