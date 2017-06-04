package com.game.movieappNano.utilities;

/**
 * Created by Sara on 6/3/2017.
 */

public class Constant {

    public static final String API_KEY = "";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";


    public enum movieType {
        POPULAR,
        TOP_RATED
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
        String url =
                "http://api.themoviedb.org/3/movie/" +
                        type +
                        "?api_key=" +
                        API_KEY;
        return url;
    }

    public static String getImageURL(String imgName, String imgSize) {
        return IMAGE_BASE_URL + imgSize + imgName;
    }
}
