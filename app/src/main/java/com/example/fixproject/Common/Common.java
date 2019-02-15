package com.example.fixproject.Common;


import com.example.fixproject.Models.Movie;
import com.example.fixproject.Retrofit2.IRestClient;
import com.example.fixproject.Retrofit2.RestClient;

public class Common {
    public static final String BASE_URL = "https://api.themoviedb.org";
    private static final String BASE_URL_YOUTUBE = "https://www.youtube.com/watch?v=";
    public static final String API_KEY = "a7733aa6ad9413b5225d28feffa45ed1";
    public static final String GENRES_URL = "/3/genre/movie/list?api_key=" + API_KEY;
    public static final String POPULAR_MOVIES_URL = "3/movie/popular?api_key=" + API_KEY;
    public static final String TOP_RATED_MOVIES_URL = "/3/movie/top_rated?api_key=" + API_KEY;
    public static final String UP_COMING_MOVIE_URL = "3/movie/upcoming?api_key=" + API_KEY;
    public static final String DETAILS_MOVIE_URL = "3/movie/{movie_id}?api_key=" + API_KEY;
    public static final String TRAILER_URL = "/3/movie/{movie_id}/videos?api_key=" + API_KEY;
    public static final String MOVIE_ID = "movie_id";
    public static final String BACKDROP_URL = "https://image.tmdb.org/t/p/w300";
    public static final String THUMBNAIL_SIZE = "https://image.tmdb.org/t/p/w500";
    public static final String PAGE = "page";

    public static final String THUMBNAIL_URL_YOUTUBE = "http://img.youtube.com/vi/%s/hqdefault.jpg";


    public static Movie currentMovie = null;
    //Youtube API
    public static final String API_KEY_YOUTUBE = "AIzaSyAz6Zi801QjhyEpSmdzvFfckW8z1647Gw4";

    public static IRestClient getAPI() {
        return RestClient.getClient(BASE_URL).create(IRestClient.class);
    }

    public static String createBaseUrlYoutube(final String key) {
        return BASE_URL_YOUTUBE + key;
    }

    public static String createURLThumbnail(String key, String urlThumbnail) {
        return String.format(urlThumbnail, key);
    }
}
