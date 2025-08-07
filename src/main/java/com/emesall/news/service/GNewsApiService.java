package com.emesall.news.service;

import org.springframework.stereotype.Service;

import com.emesall.news.dto.FeedDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GNewsApiService {
    
    public FeedDTO fetchLiveNews(String query, String category) {
        log.info("Fetching latest news from GNews API with query: {} and category: {}", query, category);
        // Implementation would go here
        return new FeedDTO();
    }
}
