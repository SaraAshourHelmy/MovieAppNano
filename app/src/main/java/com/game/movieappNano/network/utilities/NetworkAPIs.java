package com.game.movieappNano.network.utilities;

import android.content.Context;

import com.game.movieappNano.models.Movie;
import com.game.movieappNano.models.Review;
import com.game.movieappNano.models.Trailer;
import com.game.movieappNano.network.volley.NetworkRequest;

import java.util.ArrayList;

/**
 * Created by Sara on 6/30/2017.
 */

public class NetworkAPIs {


    public static void getMovies(Context context, String url, final VolleyResponse.MovieResponse movieResponse) {
        NetworkRequest.requestAPI(context, url, new VolleyResponse() {
            @Override
            public void onResponse(String response) {
                ArrayList<Movie> lstMovies = Parsing.parseMovies(response);
                movieResponse.onResponse(lstMovies);
            }

            @Override
            public void onError(String errorMsg) {

                movieResponse.onError(errorMsg);
            }
        });
    }

    public static void getTrailers(Context context, String url,
                                   final VolleyResponse.TrailerResponse trailerResponse) {
        NetworkRequest.requestAPI(context, url, new VolleyResponse() {
            @Override
            public void onResponse(String response) {
                ArrayList<Trailer> trailers = Parsing.parseTrailer(response);
                trailerResponse.onResponse(trailers);
            }

            @Override
            public void onError(String errorMsg) {

                trailerResponse.onError(errorMsg);
            }
        });
    }

    public static void getReviews(Context context, String url,
                                  final VolleyResponse.ReviewsResponse reviewsResponse) {
        NetworkRequest.requestAPI(context, url, new VolleyResponse() {
            @Override
            public void onResponse(String response) {
                ArrayList<Review> reviews = Parsing.parseReviews(response);
                reviewsResponse.onResponse(reviews);
            }

            @Override
            public void onError(String errorMsg) {

                reviewsResponse.onError(errorMsg);
            }
        });
    }
}
