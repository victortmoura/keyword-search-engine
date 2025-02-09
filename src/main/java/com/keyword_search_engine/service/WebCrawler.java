package com.keyword_search_engine.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebCrawler {
    private final String baseUrl;
    private final String keyword;
    private final Set<String> visitedUrls;
    private final List<String> foundUrls;

    public WebCrawler(String baseUrl, String keyword) {
        this.baseUrl = baseUrl;
        this.keyword = keyword.toLowerCase();
        this.visitedUrls = new HashSet<>();
        this.foundUrls = new ArrayList<>();
    }

    public List<String> crawl(String url) {
        if (visitedUrls.contains(url)) {
            return foundUrls;
        }
        visitedUrls.add(url);

        try {
            Document document = Jsoup.connect(url).get();
            String html = document.html().toLowerCase();

            if (html.contains(keyword)) {
                foundUrls.add(url);
            }

            Elements links = document.select("a[href]");
            for (Element link : links) {
                String nextUrl = link.absUrl("href");
                if (nextUrl.startsWith(baseUrl)) {
                    crawl(nextUrl);
                }
            }
        } catch (IOException e) {
            System.err.println("Error crawling URL: " + url);
        }

        return foundUrls;
    }
}