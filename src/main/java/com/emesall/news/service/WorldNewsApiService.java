package com.emesall.news.service;

import org.springframework.stereotype.Service;

import com.emesall.news.dto.FeedDTO;
import com.emesall.news.dto.WorldNewsApiResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorldNewsApiService {
    
    public WorldNewsApiResponse fetchLiveNewsPaged(String query, String category, int page, int pageSize) {
        log.info("Fetching live news from World News API with query: {}, category: {}, page: {}, pageSize: {}", 
                query, category, page, pageSize);
        // Implementation would go here
        return new WorldNewsApiResponse();
    }
    
    public FeedDTO getNews(String query, int page, int pageSize) {
        log.info("Fetching news from WorldNewsAPI for query: {}", query);
        // Implementation would go here
        return new FeedDTO();
    }
}
