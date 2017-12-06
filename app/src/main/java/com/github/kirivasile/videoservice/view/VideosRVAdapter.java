package com.github.kirivasile.videoservice.view;


import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.kirivasile.videoservice.R;
import com.github.kirivasile.videoservice.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

public class VideosRVAdapter extends RecyclerView.Adapter<VideosRVAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        JZVideoPlayerStandard videoPlayer;
        TextView videoName;
        ImageButton downloadButton;
        ImageButton deleteButton;
        ProgressBar downloadProgress;
        ProgressBar deleteProgress;

        public ViewHolder(View itemView) {
            super(itemView);
            videoPlayer = itemView.findViewById(R.id.videoPlayer);
            videoName = itemView.findViewById(R.id.videoName);
            downloadButton = itemView.findViewById(R.id.downloadButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            downloadProgress = itemView.findViewById(R.id.downloadProgress);
            deleteProgress = itemView.findViewById(R.id.deleteProgress);
        }
    }

    private List<Video> mVideos;
    private MainActivity mActivity;

    public VideosRVAdapter(MainActivity activity) {
        mVideos = new ArrayList<>();
        mActivity = activity;
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

        holder.videoPlayer.setUp(video.getVideoUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_LIST,
                video.getName());
        holder.videoName.setText(video.getName());
        holder.downloadButton.setOnClickListener((View view) -> {
            holder.downloadButton.setVisibility(View.GONE);
            holder.downloadProgress.setVisibility(View.VISIBLE);
        });
        holder.deleteButton.setOnClickListener((View view) -> {
            holder.deleteButton.setVisibility(View.GONE);
            holder.deleteProgress.setVisibility(View.VISIBLE);
        });
    }

    private void setPictureFitScreen(ImageView container, String imageUrl) {
        WindowManager wm = (WindowManager) mActivity
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Picasso.with(mActivity)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .resize(size.x, 0)
                .into(container);
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public void setVideos(List<Video> videos) {
        mVideos = new ArrayList<>(videos);
        notifyDataSetChanged();
    }
}
