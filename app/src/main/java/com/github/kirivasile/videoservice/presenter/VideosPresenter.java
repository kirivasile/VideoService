package com.github.kirivasile.videoservice.presenter;


import android.util.Log;
import android.widget.Toast;

import com.github.kirivasile.videoservice.R;
import com.github.kirivasile.videoservice.model.Video;
import com.github.kirivasile.videoservice.server.ConnectionService;
import com.github.kirivasile.videoservice.server.IAPI;
import com.github.kirivasile.videoservice.view.MainActivity;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class VideosPresenter {
    /*
    * Presenter for the main activity.
    * */
    private MainActivity mView;
    private IAPI mService;

    public VideosPresenter(MainActivity view) {
        mView = view;
        mService = ConnectionService.provideServide();
    }

    public void loadVideos() {
        mView.showProgress();
        Observable<IAPI.VideoJSON[]> observable = mService.getVideos();
        observable.map(Video::fromJSON)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((videos) -> {
                    mView.setVideos(videos);
                    mView.hideProgress();
                }, mOnError);

    }

    private Action1<Throwable> mOnError = (response) -> {
        Log.e("VIDEOS", "PresenterServerError: " + response.toString());
        Toast.makeText(mView, R.string.ConnectionError, Toast.LENGTH_LONG).show();
    };
}
