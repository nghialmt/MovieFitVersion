package com.example.fixproject.ViewHolder;

import android.app.Activity;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fixproject.R;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;


public class TrailerViewHolder extends RecyclerView.ViewHolder {
    public TextView textWaveTitle;
    public ImageView imageViewItems;
    public ImageView playButton;
    public YouTubePlayerView youTubePlayerView;

    public TrailerViewHolder(View itemView) {
        super(itemView);
        textWaveTitle = itemView.findViewById(R.id.textViewTitle);
        imageViewItems = itemView.findViewById(R.id.imageViewItem);
        playButton=itemView.findViewById(R.id.btnPlay);
        youTubePlayerView = itemView.findViewById(R.id.youtube_view);

    }


}
