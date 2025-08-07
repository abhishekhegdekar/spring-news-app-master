package com.emesall.news.controller;

import com.emesall.news.dto.FeedDTO;
import com.emesall.news.service.GNewsApiService;
import com.emesall.news.service.MediaStackApiService;
import com.emesall.news.service.NewsApiService;
import com.emesall.news.service.WorldNewsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private NewsApiService newsApiService;

    @Autowired
    private GNewsApiService gNewsApiService;

    @Autowired
    private MediaStackApiService mediaStackApiService;

	@Autowired
    private WorldNewsApiService worldNewsApiService;

    @GetMapping("/")
    public String index(@RequestParam(value = "category", required = false) String category,
                       @RequestParam(value = "query", required = false) String query,
                       @RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "10") int size,
                       Model model) {
        
        // Validate page and size parameters
        if (page < 1) {
            return "redirect:/?page=1&size=" + size;
        }
        if (size < 1 || size > 50) {
            size = 10; // Default to 10 if size is invalid
        }
        
        List<FeedDTO> allFeeds = fetchAllNews(category, query);
        
        // Calculate pagination
        int totalArticles = allFeeds.size();
        int totalPages = (int) Math.ceil((double) totalArticles / size);
        
        // Handle out-of-bounds page
        if (page > totalPages && totalPages > 0) {
            return "redirect:/?page=1&size=" + size;
        }
        
        // Get subset for current page
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, totalArticles);
        List<FeedDTO> pageFeeds = new ArrayList<>();
        
        if (startIndex < totalArticles) {
            pageFeeds = allFeeds.subList(startIndex, endIndex);
        }
        
        // Add model attributes
        model.addAttribute("results", pageFeeds);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalArticles", totalArticles);
        model.addAttribute("hasNextPage", page < totalPages);
        model.addAttribute("hasPreviousPage", page > 1);
        model.addAttribute("category", category);
        model.addAttribute("query", query);
        
        return "index";
    }
    
    @GetMapping("/api/news")
    @ResponseBody
    public Map<String, Object> getNewsApi(@RequestParam(value = "category", required = false) String category,
                                         @RequestParam(value = "query", required = false) String query,
                                         @RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        
        Map<String, Object> response = new HashMap<>();
        
        // Validate parameters
        if (page < 1) {
            response.put("error", "Invalid page number");
            return response;
        }
        if (size < 1 || size > 50) {
            size = 10;
        }
        
        List<FeedDTO> allFeeds = fetchAllNews(category, query);
        
        // Calculate pagination
        int totalArticles = allFeeds.size();
        int totalPages = (int) Math.ceil((double) totalArticles / size);
        
        // Handle out-of-bounds page
        if (page > totalPages && totalPages > 0) {
            response.put("error", "Page out of bounds");
            return response;
        }
        
        // Get subset for current page
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, totalArticles);
        List<FeedDTO> pageFeeds = new ArrayList<>();
        
        if (startIndex < totalArticles) {
            pageFeeds = allFeeds.subList(startIndex, endIndex);
        }
        
        // Build response
        response.put("articles", pageFeeds);
        response.put("currentPage", page);
        response.put("pageSize", size);
        response.put("totalPages", totalPages);
        response.put("totalArticles", totalArticles);
        response.put("hasNextPage", page < totalPages);
        response.put("hasPreviousPage", page > 1);
        
        return response;
    }
    
    private List<FeedDTO> fetchAllNews(String category, String query) {
        List<FeedDTO> allFeeds = new ArrayList<>();
        
        try {
            // Fetch from NewsAPI
            List<FeedDTO> newsApiFeeds = newsApiService.fetchLiveNews(category, query);
            allFeeds.addAll(newsApiFeeds);
        } catch (Exception e) {
            // Log error but continue with other APIs
        }
        
        try {
            // Fetch from GNews
            List<FeedDTO> gNewsFeeds = gNewsApiService.fetchLiveNews(category, query);
            allFeeds.addAll(gNewsFeeds);
        } catch (Exception e) {
            // Log error but continue with other APIs
        }
        
        try {
            // Fetch from MediaStack
            List<FeedDTO> mediaStackFeeds = mediaStackApiService.fetchLiveNews(category, query);
            allFeeds.addAll(mediaStackFeeds);
        } catch (Exception e) {
            // Log error but continue with other APIs
        }
        
        try {
            // Fetch from WorldNewsAPI
            WorldNewsApiService.WorldNewsApiResponse worldNewsResponse = 
                worldNewsApiService.fetchLiveNewsPaged(category, query, 1, 10);
            allFeeds.addAll(worldNewsResponse.getArticles());
        } catch (Exception e) {
            // Log error but continue
        }
        
        // Remove duplicates based on title
        List<FeedDTO> uniqueFeeds = new ArrayList<>();
        for (FeedDTO feed : allFeeds) {
            boolean isDuplicate = false;
            for (FeedDTO existingFeed : uniqueFeeds) {
                if (feed.getTitle() != null && feed.getTitle().equals(existingFeed.getTitle())) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                uniqueFeeds.add(feed);
            }
        }
        
        return uniqueFeeds;
    }
} 