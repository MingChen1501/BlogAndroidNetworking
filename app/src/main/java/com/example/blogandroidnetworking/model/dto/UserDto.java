package com.example.blogandroidnetworking.model.dto;

public class UserDto {
    private String id;
    private String name;
    private String type;
    private String avatar;

    public UserDto() {
    }

    public UserDto(String id, String name, String type, String avatar) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
