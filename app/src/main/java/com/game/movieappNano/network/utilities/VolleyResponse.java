package com.game.movieappNano.network.utilities;

import com.game.movieappNano.models.Movie;
import com.game.movieappNano.models.Review;
import com.game.movieappNano.models.Trailer;

import java.util.ArrayList;

/**
 * Created by Sara on 6/3/2017.
 */

public interface VolleyResponse {

    void onResponse(String response);

    void onError(String errorMsg);

    interface MovieResponse {

        void onResponse(ArrayList<Movie> movies);

        void onError(String errorMsg);
    }

    interface TrailerResponse {
        void onResponse(ArrayList<Trailer> trailers);

        void onError(String errorMsg);
    }

    interface ReviewsResponse {
        void onResponse(ArrayList<Review> reviews);

        void onError(String errorMsg);
    }
}
