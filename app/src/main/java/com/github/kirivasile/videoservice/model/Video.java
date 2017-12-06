package com.github.kirivasile.videoservice.model;


public class Video {
    private String name;
    private String videoUrl;

    public Video(String name, String videoUrl) {
        this.name = name;
        this.videoUrl = videoUrl;
    }

    public String getName() {
        return name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
