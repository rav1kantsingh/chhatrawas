package com.ravikantsingh.hackathon.Admin;

public class AdminModal{
    String name;
    String time;
    String message;
    String imageUrl;

    public AdminModal(String name, String time, String message, String imageUrl) {
        this.name = name;
        this.time = time;
        this.message = message;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}