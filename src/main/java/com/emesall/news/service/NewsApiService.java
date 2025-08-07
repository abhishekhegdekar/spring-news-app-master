package com.emesall.news.service;

import org.springframework.stereotype.Service;

import com.emesall.news.dto.FeedDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NewsApiService {
    
    public FeedDTO fetchLiveNews(String query, String category) {
        log.info("Fetching latest news from News API with query: {} and category: {}", query, category);
        // Implementation would go here
        return new FeedDTO();
    }
}
