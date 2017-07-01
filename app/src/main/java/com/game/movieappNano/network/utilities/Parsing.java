package com.game.movieappNano.network.utilities;

import android.util.Log;

import com.game.movieappNano.models.Movie;
import com.game.movieappNano.models.Review;
import com.game.movieappNano.models.Trailer;
import com.game.movieappNano.utilities.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
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

    public static ArrayList<Trailer> parseTrailer(String json) {
        ArrayList<Trailer> trailers = new ArrayList<>();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Trailer>>() {
            }.getType();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            trailers = gson.fromJson(jsonArray.toString(), type);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailers;
    }

    public static ArrayList<Review> parseReviews(String json) {
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Review>>() {
            }.getType();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            reviews = gson.fromJson(jsonArray.toString(), type);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
