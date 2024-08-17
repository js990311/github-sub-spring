package com.githubsub.domain.crawler.service.crawling;

/**
 * 링크를 집어넣으면 T를 반환하는 크롤링 서비스
 * @param <T>
 */
public interface Crawler<T> {
    public T crawling(String url);
}
