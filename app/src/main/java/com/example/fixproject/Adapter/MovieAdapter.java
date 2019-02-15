package com.example.fixproject.Adapter;

import android.content.Context;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fixproject.Common.Common;
import com.example.fixproject.DetailsActivity;
import com.example.fixproject.Listeners.OnViewItemListener;
import com.example.fixproject.Models.Movie;
import com.example.fixproject.R;
import com.example.fixproject.ViewHolder.MovieViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private final List<Movie> lists;
    private final Context context;

    public MovieAdapter(List<Movie> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movies, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, final int i) {
        Picasso.with(context).load(Common.BACKDROP_URL + lists.get(i).getPoster_path()).into(movieViewHolder.imageThumbnail);
        movieViewHolder.titleMovie.setText(lists.get(i).getTitle());
        movieViewHolder.txtRating.setText(String.valueOf(lists.get(i).getVote_average()));
        movieViewHolder.ratingBar.setRating(Float.parseFloat(lists.get(i).getVote_average() / 2 + ""));
        movieViewHolder.setItemListener(new OnViewItemListener() {
            @Override
            public void onClick(View v) {
                Common.currentMovie = lists.get(i);
                Intent detailIntent = new Intent(context, DetailsActivity.class);
                context.startActivity(detailIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
