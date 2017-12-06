package com.github.kirivasile.videoservice.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.github.kirivasile.videoservice.R;
import com.github.kirivasile.videoservice.model.Video;
import com.github.kirivasile.videoservice.presenter.VideosPresenter;

import java.util.List;

import cn.jzvd.JZVideoPlayer;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private VideosRVAdapter mAdapter;
    private VideosPresenter mPresenter;
    private FrameLayout mProgressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressLayout = (FrameLayout) findViewById(R.id.videoProgressLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.videosRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new VideosRVAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter = new VideosPresenter(this);
        mPresenter.loadVideos();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    public void setVideos(List<Video> videos) {
        mAdapter.setVideos(videos);
    }

    public void showProgress() {
        mProgressLayout.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    public void hideProgress() {
        mProgressLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
