package com.example.blogandroidnetworking.model;

import java.util.Arrays;

import lombok.Builder;


public class Post {
    private long id;

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    private long authorId;
    private String authorName;
    private String authorAvatar;
    private String title;
    private String content;
    private String[] imageUrl = new String[0];
    private String publishedAt;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public Post() {
    }

    public Post(long id,long authorId , String authorName, String title,
                String content, String[] imageUrl, String publishedAt) {
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getImageUrl() {
        return imageUrl;
    }

    public void setImagesUrl(String[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", authorAvatar='" + authorAvatar + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl=" + Arrays.toString(imageUrl) +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }
}
