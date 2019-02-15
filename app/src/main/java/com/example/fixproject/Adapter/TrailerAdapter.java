package com.example.fixproject.Adapter;

import android.app.Activity;
import android.app.WallpaperInfo;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.fixproject.Common.Common;
import com.example.fixproject.Models.Result;
import com.example.fixproject.R;
import com.example.fixproject.ViewHolder.TrailerViewHolder;


import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {
    private Context context;
    private List<Result> lists;

    public TrailerAdapter(Context context, List<Result> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_youtube, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrailerViewHolder holder, final int position) {

        holder.textWaveTitle.setText(lists.get(position).getName());
        Picasso.with(context).load(Common.createURLThumbnail(lists.get(position).getKey(), Common.THUMBNAIL_URL_YOUTUBE)).into(holder.imageViewItems);
        holder.playButton.setVisibility(View.VISIBLE);
        holder.imageViewItems.setVisibility(View.VISIBLE);
        holder.youTubePlayerView.setVisibility(View.GONE);


        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.youTubePlayerView.isActivated()) {
                    holder.youTubePlayerView.release();
                    holder.playButton.setVisibility(View.VISIBLE);
                    holder.imageViewItems.setVisibility(View.VISIBLE);
                    holder.youTubePlayerView.setVisibility(View.GONE);
                } else {


                    holder.playButton.setVisibility(View.GONE);
                    holder.imageViewItems.setVisibility(View.GONE);
                    holder.youTubePlayerView.setVisibility(View.VISIBLE);

                    holder.youTubePlayerView.initialize(initializedYouTubePlayer -> initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady() {
                            initializedYouTubePlayer.loadVideo(lists.get(position).getKey(), 0);
                        }
                    }), true);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return lists.size();
    }



}
