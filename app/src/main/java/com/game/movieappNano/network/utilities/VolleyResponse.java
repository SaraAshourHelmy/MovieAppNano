package com.game.movieappNano.network.utilities;

import com.game.movieappNano.models.Movie;

import java.util.ArrayList;

/**
 * Created by Sara on 6/3/2017.
 */

public interface VolleyResponse {

    void onResponse(ArrayList<Movie> movies);

    void onError(String errorMsg);
}
