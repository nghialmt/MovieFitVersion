package com.example.fixproject.Retrofit2;


import com.example.fixproject.Common.Common;
import com.example.fixproject.Models.Detail;
import com.example.fixproject.Models.Genres;
import com.example.fixproject.Models.Response;
import com.example.fixproject.Models.Trailer;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRestClient {
    // Gets a list of popular movies.
    @GET(Common.POPULAR_MOVIES_URL)
    Observable<Response> getPopularMovies(@Query(Common.PAGE) int page);

    // Gets a list of the top rated movies
    @GET(Common.TOP_RATED_MOVIES_URL)
    Observable<Response> getTopRatedMovies(@Query(Common.PAGE) int page);

    @GET(Common.UP_COMING_MOVIE_URL)
    Observable<Response> getUpComingMOvies(@Query(Common.PAGE) int page);

    @GET(Common.DETAILS_MOVIE_URL)
    Call<Detail> getDetailsMovie(@Path("movie_id") int id);

    @GET(Common.TRAILER_URL)
    Observable<Trailer> getTrailerMovie(@Path("movie_id") int id);

    //Gets a list of genres
    @GET(Common.GENRES_URL)
    Observable<Genres> getGenres();
}
