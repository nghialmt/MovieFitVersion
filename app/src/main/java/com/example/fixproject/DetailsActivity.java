package com.example.fixproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fixproject.Common.Common;
import com.example.fixproject.Models.Detail;
import com.example.fixproject.Models.Trailer;
import com.example.fixproject.Retrofit2.IRestClient;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    private IRestClient mServer;
    private CompositeDisposable disposable = new CompositeDisposable();
    private TextView txtTitle;
    private TextView txt_vote_count, txt_vote_average, txt_year, txt_duration;
    private ImageView image_collapsing;
    private ImageButton trailerPlay;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        MappingAndConnect();


        FetchDataInfo();
        youtubeAPIFetchData();
    }

    private void youtubeAPIFetchData() {
        trailerPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(DetailsActivity.this,TrailersActivity.class);
                startActivity(intent);
            }
        });
    }

    private void FetchDataInfo() {
        mServer.getDetailsMovie(Common.currentMovie.getId()).enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                Detail detail = response.body();
                if (detail != null) {
                    Picasso.with(getApplicationContext()).load(Common.THUMBNAIL_SIZE + detail.getBackdrop_path()).into(image_collapsing);
                    txtTitle.setText(detail.getTitle());
                    txt_vote_count.setText(new StringBuilder(String.valueOf(detail.getVote_average())).append("/10 AVG"));
                    txt_vote_average.setText(new StringBuilder("Vote count : ").append(String.valueOf(detail.getVote_count())));

                    String[] datePlite = detail.getRelease_date().split("-");
                    txt_year.setText(datePlite[0]);
                    txt_duration.setText(new StringBuilder(String.valueOf(detail.getRuntime() / 60)).append(" hr:").append(detail.getRuntime() % 60).append(" min"));

                } else {
                    Log.d("Kiem tra", "Failed");
                }
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                Log.d("Kiem tra", "Fialed");
            }
        });

    }

    private void MappingAndConnect() {
        mServer = Common.getAPI();
        txtTitle = (TextView) findViewById(R.id.title_main);
        txt_vote_count = (TextView) findViewById(R.id.vote_count);
        txt_vote_average = (TextView) findViewById(R.id.vote_average);
        txt_year = (TextView) findViewById(R.id.year);
        txt_duration = (TextView) findViewById(R.id.duration);
        image_collapsing = findViewById(R.id.image_collapsing);
        trailerPlay = findViewById(R.id.trailerPlay);
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposable.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
