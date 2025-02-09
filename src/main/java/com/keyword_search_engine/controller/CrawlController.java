package com.keyword_search_engine.controller;

import com.keyword_search_engine.model.CrawlRequest;
import com.keyword_search_engine.model.CrawlResponse;
import com.keyword_search_engine.model.CrawlResult;
import com.keyword_search_engine.service.CrawlService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crawl")
@AllArgsConstructor
public class CrawlController {

    private CrawlService crawlService;

    @PostMapping
    public ResponseEntity<?> startCrawl(@RequestBody @Valid CrawlRequest request) {
        String keyword = request.keyword();
        String id = crawlService.startCrawl(keyword);
        return ResponseEntity.ok(new CrawlResponse(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCrawlResult(@PathVariable String id) {
        CrawlResult result = crawlService.getCrawlResult(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

}
