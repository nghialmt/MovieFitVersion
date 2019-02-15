package com.example.fixproject.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.fixproject.Adapter.MovieAdapter;
import com.example.fixproject.Common.Common;
import com.example.fixproject.Models.Banner;
import com.example.fixproject.Models.Movie;
import com.example.fixproject.Models.Response;
import com.example.fixproject.R;
import com.example.fixproject.Retrofit2.IRestClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.supercharge.shimmerlayout.ShimmerLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private SliderLayout sliderMovieReview;
    private DatabaseReference movie;
    private FirebaseDatabase database;
    private HashMap<String, String> imageList;
    private CompositeDisposable disposable = new CompositeDisposable();
    private IRestClient mServer;
    private RecyclerView recyclerMoviePopular, recyclerTopRatedMovie, recyclerUpComingdMovie;
    private LinearLayoutManager linearLayoutManager;
    private ShimmerLayout mShimmerViewContainer;

    public MovieFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        MappingAndConnect(view);
        getDataBannerFirebase();
        //POPULAR
        getDataForPopularMovies();
        //TOP RATED
        getDataForTopRateMovies();
        //Up Coming
        getDataForUpComingMovies();
        return view;
    }

    private void getDataForUpComingMovies() {
        disposable.add(mServer.getUpComingMOvies(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response response) throws Exception {
                        listUpComingData(response.getResults());
                    }
                })
        );
    }

    private void listUpComingData(List<Movie> results) {
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerUpComingdMovie.setLayoutManager(linearLayoutManager);
        MovieAdapter adapter = new MovieAdapter(results, getContext());


        recyclerUpComingdMovie.setAdapter(adapter);
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    private void getDataForTopRateMovies() {
        disposable.add(mServer.getTopRatedMovies(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response response) throws Exception {
                        listTopRateData(response.getResults());
                    }
                })
        );
    }

    private void listTopRateData(List<Movie> results) {
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTopRatedMovie.setLayoutManager(linearLayoutManager);
        MovieAdapter adapter = new MovieAdapter(results, getContext());
        recyclerTopRatedMovie.setAdapter(adapter);

    }

    private void getDataForPopularMovies() {
        disposable.add(mServer.getPopularMovies(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response response) throws Exception {
                        listPopularMovieData(response.getResults());
                    }
                }));
    }

    private void listPopularMovieData(List<Movie> results) {
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerMoviePopular.setLayoutManager(linearLayoutManager);
        MovieAdapter adapter = new MovieAdapter(results, getContext());
        recyclerMoviePopular.setAdapter(adapter);

    }


    private void getDataBannerFirebase() {

        imageList = new HashMap<>();
        movie.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    Banner banner = value.getValue(Banner.class);
                    imageList.put(banner.getName(), banner.getImage());
                }

                for (String name : imageList.keySet()) {
                    TextSliderView textSliderView = new TextSliderView(getContext());
                    textSliderView.description(name)
                            .image(imageList.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit);

                    sliderMovieReview.addSlider(textSliderView);
                    movie.removeEventListener(this);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        sliderMovieReview.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderMovieReview.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderMovieReview.setCustomAnimation(new DescriptionAnimation());
        sliderMovieReview.setDuration(4000);
    }

    private void MappingAndConnect(View view) {
        //Firebase
        mShimmerViewContainer = (ShimmerLayout) view.findViewById(R.id.shimmer_view_container);
        database = FirebaseDatabase.getInstance();
        movie = database.getReference("Movies");
        sliderMovieReview = (SliderLayout) view.findViewById(R.id.sliderMovieReview);
        recyclerMoviePopular = (RecyclerView) view.findViewById(R.id.recyclerPopularMovie);
        recyclerTopRatedMovie = (RecyclerView) view.findViewById(R.id.recyclerTopRatedMovie);
        recyclerUpComingdMovie = (RecyclerView) view.findViewById(R.id.recyclerUpComingdMovie);
        recyclerMoviePopular.setItemAnimator(new DefaultItemAnimator());
        recyclerTopRatedMovie.setItemAnimator(new DefaultItemAnimator());
        recyclerUpComingdMovie.setItemAnimator(new DefaultItemAnimator());

        mServer = Common.getAPI();
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
}
