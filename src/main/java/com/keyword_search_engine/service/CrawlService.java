package com.keyword_search_engine.service;

import com.keyword_search_engine.model.CrawlResult;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CrawlService {

    private Map<String, CrawlResult> crawlResults;
    private String baseUrl;

    public CrawlService() {
        String baseUrl = System.getenv("BASE_URL");
        this.baseUrl = baseUrl != null ? baseUrl : "http://hiring.axreng.com/";
        this.crawlResults = new HashMap<>();
    }
    public String startCrawl(String keyword) {
        String id = generateId();
        CrawlResult result = new CrawlResult(id, "active", new ArrayList<>());
        crawlResults.put(id, result);

        new Thread(() -> {
            WebCrawler crawler = new WebCrawler(baseUrl, keyword);
            List<String> urls = crawler.crawl(baseUrl);

            CrawlResult updatedResult = new CrawlResult(id, "done", urls);

            crawlResults.put(id, updatedResult);
        }).start();

        return id;
    }

    public CrawlResult getCrawlResult(String id) {
        return crawlResults.get(id);
    }

    private String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}