package com.game.movieappNano.network.utilities;

import com.game.movieappNano.models.Movie;
import com.game.movieappNano.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sara on 6/3/2017.
 */

public class Parsing {

    public static ArrayList<Movie> parseMovies(String json) {
        ArrayList<Movie> lstMovies = new ArrayList<>();
        try {
            JSONObject result = new JSONObject(json);
            JSONArray movies = result.getJSONArray("results");
            Movie movie;
            for (int i = 0; i < movies.length(); i++) {
                movie = new Movie();
                JSONObject jsonMovie = movies.getJSONObject(i);
                movie.setId(jsonMovie.getString("id"));
                movie.setImgURL(Constant.getImageURL
                        (jsonMovie.getString("poster_path"),
                                Constant.imageSize.w342.name()));
                movie.setOriginalTitle(jsonMovie.getString("original_title"));
                movie.setOverview(jsonMovie.getString("overview"));
                movie.setVoteAverage(jsonMovie.getString("vote_average"));
                movie.setReleaseDate(jsonMovie.getString("release_date"));

                // add movie to list
                lstMovies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lstMovies;
    }
}
