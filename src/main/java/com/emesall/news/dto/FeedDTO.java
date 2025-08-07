package com.emesall.news.dto;

import java.net.URI;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FeedDTO {
    private Long id;
    private String title;
    private String entry;
    private String imageUrl;
    private URI uri;
    private String publishedAgo;
    private List<String> categories;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getEntry() { return entry; }
    public void setEntry(String entry) { this.entry = entry; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public URI getUri() { return uri; }
    public void setUri(URI uri) { this.uri = uri; }

    public String getPublishedAgo() { return publishedAgo; }
    public void setPublishedAgo(String publishedAgo) { this.publishedAgo = publishedAgo; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }

    public String getFormattedDate() {
        if (publishedAgo != null) {
            try {
                Instant instant = Instant.parse(publishedAgo);
                return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(instant);
            } catch (Exception e) {
                return publishedAgo;
            }
        }
        return "";
    }
}
