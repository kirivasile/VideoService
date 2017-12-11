package com.github.kirivasile.videoservice.server;


import rx.Observable;

import retrofit2.http.GET;

public interface IAPI {
    /*
    * Interface for all the HTTP requests
    * */

    class VideoJSON {
        public int id;
        public String name;
        public String videoUrl;
    }

    @GET("/")
    Observable<VideoJSON[]> getVideos();
}
