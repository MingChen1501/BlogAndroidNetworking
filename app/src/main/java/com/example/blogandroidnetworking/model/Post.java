package com.example.blogandroidnetworking.model;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Post {
    private long id;
    private Author author;
    private String title;
    private String content;
    private String[] imageUrl;
    private String publishedAt;

    public Post() {
    }

    public Post(long id, Author author, String title, String content, String[] imageUrl, String publishedAt) {
        this.id = id;
        this.author = author;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    public void setImageUrl(String[] imageUrl) {
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
                ", author=" + author +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl=" + Arrays.toString(imageUrl) +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }
}
