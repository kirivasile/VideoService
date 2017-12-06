package com.github.kirivasile.videoservice.view;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kirivasile.videoservice.R;
import com.github.kirivasile.videoservice.model.Video;
import com.github.kirivasile.videoservice.utils.VideoDB;
import com.github.kirivasile.videoservice.utils.VideoDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideosRVAdapter extends RecyclerView.Adapter<VideosRVAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public JZVideoPlayerStandard videoPlayer;
        TextView videoName;
        public ImageButton downloadButton;
        public ProgressBar downloadProgress;

        public ViewHolder(View itemView) {
            super(itemView);
            videoPlayer = itemView.findViewById(R.id.videoPlayer);
            videoName = itemView.findViewById(R.id.videoName);
            downloadButton = itemView.findViewById(R.id.downloadButton);
            downloadProgress = itemView.findViewById(R.id.downloadProgress);
        }
    }

    private List<Video> mVideos;
    private MainActivity mActivity;
    private Set<Integer> mSavedVideos;

    public VideosRVAdapter(MainActivity activity) {
        mVideos = new ArrayList<>();
        mActivity = activity;

        VideoDB db = VideoDB.getInstance(mActivity.getApplicationContext());
        mSavedVideos = new HashSet<>(db.getSavedVideos());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.card_in_videos, parent,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Video video = mVideos.get(position);

        boolean isSaved = mSavedVideos.contains(video.getId());
        if (isSaved) {
            holder.downloadButton.setColorFilter(Color.GREEN);
            File videoFile = new File(mActivity.getFilesDir() + File.separator
                    + VideoDownloader.VIDEO_DIR + File.separator + Integer.toString(video.getId()));
            try {
                Uri uri = Uri.parse(videoFile.getCanonicalPath());
                String path = "file://" + uri.toString();
                holder.videoPlayer.setUp(path, JZVideoPlayerStandard.SCREEN_WINDOW_LIST,
                        video.getName());
            } catch (IOException e) {
                Log.e("VideosAdapter", "Failed to get videoFile path: " + e.toString());
            }
        } else {
            holder.downloadButton.setColorFilter(Color.BLACK);

            holder.videoPlayer.setUp(video.getVideoUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_LIST,
                    video.getName());
        }
        holder.videoName.setText(video.getName());
        holder.downloadButton.setOnClickListener((View view) -> {
            JZVideoPlayer.releaseAllVideos();
            holder.downloadButton.setVisibility(View.GONE);
            holder.downloadProgress.setVisibility(View.VISIBLE);
            if (isSaved) {
                File videoFile = new File(mActivity.getFilesDir() + File.separator
                        + VideoDownloader.VIDEO_DIR + File.separator +
                        Integer.toString(video.getId()));
                if (videoFile.exists()) {
                    videoFile.delete();
                    Toast.makeText(mActivity, R.string.VideoDeleted, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mActivity, R.string.VideoNotFound, Toast.LENGTH_LONG).show();
                }
                VideoDB.getInstance(mActivity.getApplicationContext()).deleteVideo(video.getId());
                this.markVideoAsUnsaved(video.getId());
                holder.downloadButton.setVisibility(View.VISIBLE);
                holder.downloadProgress.setVisibility(View.GONE);
                holder.downloadButton.setColorFilter(Color.BLACK);
                holder.videoPlayer.setUp(video.getVideoUrl(),
                        JZVideoPlayerStandard.SCREEN_WINDOW_LIST,
                        video.getName());
            } else {
                new VideoDownloader(mActivity, holder, video, this).execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public void setVideos(List<Video> videos) {
        mVideos = new ArrayList<>(videos);
        notifyDataSetChanged();
    }

    public void markVideoAsSaved(int id) {
        mSavedVideos.add(id);
    }

    public void markVideoAsUnsaved(int id) {
        mSavedVideos.remove(id);
    }
}
