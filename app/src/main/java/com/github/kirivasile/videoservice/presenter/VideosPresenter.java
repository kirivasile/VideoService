package com.github.kirivasile.videoservice.presenter;


import com.github.kirivasile.videoservice.model.Video;
import com.github.kirivasile.videoservice.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class VideosPresenter {
    private MainActivity mView;

    public VideosPresenter(MainActivity view) {
        mView = view;
    }

    public void loadVideos() {
        mView.showProgress();
        List<Video> videos = new ArrayList<>();
        Video video = new Video("Временный курс",
                "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4");
        videos.add(video);
        videos.add(video);
        videos.add(video);
        mView.setVideos(videos);
        mView.hideProgress();
    }
}
