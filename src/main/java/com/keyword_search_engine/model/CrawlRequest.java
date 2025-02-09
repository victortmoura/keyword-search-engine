package com.keyword_search_engine.model;

import jakarta.validation.constraints.Size;

public record CrawlRequest(
        @Size(min = 4, max = 32, message = "Keyword must be between 4 and 32 characters.")
        String keyword
) {}