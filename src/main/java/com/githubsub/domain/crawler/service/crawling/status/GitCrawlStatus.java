package com.githubsub.domain.crawler.service.crawling.status;

import java.util.LinkedList;
import java.util.Queue;

public class GitCrawlStatus {
    private Queue<String> queue;

    public GitCrawlStatus() {
        this.queue = new LinkedList<>();
    }

    public boolean isNotEmpty(){
        return !queue.isEmpty();
    }

    public String next(){
        return queue.poll();
    }
}
