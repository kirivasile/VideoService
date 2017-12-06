package com.github.kirivasile.videoservice.model;


public class Video {
    private int id;
    private String name;
    private String videoUrl;

    public Video(int id, String name, String videoUrl) {
        this.id = id;
        this.name = name;
        this.videoUrl = videoUrl;
    }

    public String getName() {
        return name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public int getId() {
        return id;
    }
}
