package com.example.fixproject;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.fixproject.Adapter.TrailerAdapter;
import com.example.fixproject.Common.Common;
import com.example.fixproject.Models.Result;
import com.example.fixproject.Models.Trailer;
import com.example.fixproject.Retrofit2.IRestClient;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TrailersActivity extends AppCompatActivity {
    private CompositeDisposable disposable = new CompositeDisposable();
    private IRestClient mServer;
    private String key;
    private RecyclerView recyclerTrailer;
    private TrailerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);
        mServer = Common.getAPI();
        recyclerTrailer = findViewById(R.id.recyclerTrailer);
        recyclerTrailer.setHasFixedSize(true);
        recyclerTrailer.setLayoutManager(new LinearLayoutManager(this));
        recyclerTrailer.setItemAnimator(new DefaultItemAnimator());
        disposable.add(mServer.getTrailerMovie(Common.currentMovie.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Trailer>() {
                    @Override
                    public void accept(Trailer trailer) throws Exception {
                        fetchTrailer(trailer.getResults());
                    }
                }));
    }

    private void fetchTrailer(List<Result> results) {
        adapter = new TrailerAdapter(this, results);
        recyclerTrailer.setAdapter(adapter);
    }
}
