package com.game.movieappNano.utilities;

import android.net.Uri;

/**
 * Created by Sara on 6/3/2017.
 */

public class Constant {

    public static final String API_KEY = "";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String QUERY_KEY = "api_key";


    public enum movieInfo {
        VIDEOS,
        REVIEWS
    }

    public enum movieType {
        POPULAR,
        TOP_RATED,
        Favorite
    }

    public enum imageSize {
        w92,
        w154,
        w185,
        w342,
        w500,
        w780,
        original
    }

    public static String getURL(String type) {
        Uri uri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(type)
                .appendQueryParameter(QUERY_KEY, API_KEY)
                .build();
        return uri.toString();
    }

    public static String getImageURL(String imgName, String imgSize) {
        return IMAGE_BASE_URL + imgSize + imgName;
    }

    public static String getMovieInfoURL(String movieId, String type) {
        Uri uri = Uri.parse(BASE_URL)
                .buildUpon().appendPath(movieId)
                .appendPath(type)
                .appendQueryParameter(QUERY_KEY, API_KEY)
                .build();
        return uri.toString();
    }
}
