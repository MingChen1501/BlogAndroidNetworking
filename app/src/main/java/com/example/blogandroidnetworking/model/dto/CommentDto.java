package com.example.blogandroidnetworking.model.dto;

public class CommentDto {
    private String id;
    private String userId;
    private String content;
    private String avatar_url;
    private String username;
    private String publishedAt;


    public CommentDto(String id, String userId, String content, String avatar_url, String username, String publishedAt) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.avatar_url = avatar_url;
        this.username = username;
        this.publishedAt = publishedAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public CommentDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}