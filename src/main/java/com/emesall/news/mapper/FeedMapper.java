package com.emesall.news.mapper;

import com.emesall.news.dto.FeedDTO;
import java.util.Map;

public class FeedMapper {
    public FeedDTO map(Map<String, Object> feed) {
        FeedDTO dto = new FeedDTO();
        dto.setTitle((String) feed.get("title"));
        dto.setEntry((String) feed.get("entry"));
        dto.setImageUrl((String) feed.get("imageUrl"));
        return dto;
    }
}
