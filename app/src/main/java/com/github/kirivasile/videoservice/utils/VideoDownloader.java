package com.github.kirivasile.videoservice.utils;


import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

import com.github.kirivasile.videoservice.MyApplication;
import com.github.kirivasile.videoservice.R;
import com.github.kirivasile.videoservice.model.Video;
import com.github.kirivasile.videoservice.view.VideosRVAdapter;

import cn.jzvd.JZVideoPlayerStandard;

public class VideoDownloader extends AsyncTask<Void, Void, Boolean> {
    private WeakReference<Context> mContextRef;
    private VideosRVAdapter.ViewHolder mHolder;
    private Video mVideoData;
    private VideosRVAdapter mAdapter;

    public static final String VIDEO_DIR = "VideoServiceVideos";

    public VideoDownloader(Context context, VideosRVAdapter.ViewHolder holder,
                           Video videoData, VideosRVAdapter adapter) {
        mContextRef = new WeakReference<>(context);
        mHolder = holder;
        mVideoData = videoData;
        mAdapter = adapter;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Context context = mContextRef.get();
        if (context != null) {
            if (result) {
                Toast.makeText(context, R.string.VideoDownloaded, Toast.LENGTH_LONG).show();
                VideoDB.getInstance(context.getApplicationContext()).saveVideo(mVideoData);
                mAdapter.markVideoAsSaved(mVideoData.getId());
                mHolder.downloadButton.setColorFilter(Color.GREEN);
                File videoFile = new File(context.getFilesDir() + File.separator
                        + VideoDownloader.VIDEO_DIR + File.separator + Integer.toString(mVideoData.getId()));
                try {
                    Uri uri = Uri.parse(videoFile.getCanonicalPath());
                    String path = "file://" + uri.toString();
                    mHolder.videoPlayer.setUp(path, JZVideoPlayerStandard.SCREEN_WINDOW_LIST,
                            mVideoData.getName());
                } catch (IOException e) {
                    Log.e("VideosAdapter", "Failed to get videoFile path: " + e.toString());
                }
            } else {
                Toast.makeText(context, R.string.Error, Toast.LENGTH_LONG).show();
            }
        }
        mHolder.downloadProgress.setVisibility(View.GONE);
        mHolder.downloadButton.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean doInBackground(Void... args) {
        try {
            Context context = mContextRef.get();
            if (context == null) {
                throw new RuntimeException("Failed to get context");
            }
            URL url = new URL(mVideoData.getVideoUrl());
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            BufferedInputStream inStream = new BufferedInputStream(is, 1024 * 5);
            File videoDir = new File(context.getFilesDir() + File.separator + VIDEO_DIR);
            if (!videoDir.exists()) {
                videoDir.mkdirs();
            }
            File videoFile = new File(videoDir, Integer.toString(mVideoData.getId()));
            if (videoFile.exists()) {
                return false;
            }

            FileOutputStream outStream = new FileOutputStream(videoFile);
            byte[] buff = new byte[5 * 1024];

            int len;
            while ((len = inStream.read(buff)) != -1) {
                outStream.write(buff,0,len);
            }
            outStream.flush();
            outStream.close();
            inStream.close();
            return true;
        } catch (Exception e) {
            Log.e("VIDEO_LOAD", "doInBackground: " + e.toString() );
        }
        return false;
    }
}
