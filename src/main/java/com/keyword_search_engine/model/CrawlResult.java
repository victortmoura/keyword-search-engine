package com.keyword_search_engine.model;

import java.util.List;

public record CrawlResult(String id, String status, List<String> urls) {
}