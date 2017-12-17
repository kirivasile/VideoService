package com.github.kirivasile.videoservice.model;


import com.github.kirivasile.videoservice.server.IAPI;

import java.util.ArrayList;
import java.util.List;

public class Video {
    /*
    * Information about video
    * */
    private int id;
    private String name;
    private String videoUrl;

    public Video(int id, String name, String videoUrl) {
        this.id = id;
        this.name = name;
        this.videoUrl = videoUrl;
    }

    public Video(IAPI.VideoJSON json) {
        this.id = json.id;
        this.name = json.name;
        this.videoUrl = json.videoUrl;
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

    public static List<Video> fromJSON(IAPI.VideoJSON[] jsons) {
        List<Video> res = new ArrayList<>();
        for (IAPI.VideoJSON json: jsons) {
            res.add(new Video(json));
        }
        return res;
    }
}
