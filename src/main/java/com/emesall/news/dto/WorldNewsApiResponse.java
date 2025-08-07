package com.emesall.news.dto;

import java.util.List;

import com.emesall.news.model.Feed;

import lombok.Data;

@Data
public class WorldNewsApiResponse {
    private List<Feed> feeds;
    private int totalResults;
    private String status;
}
