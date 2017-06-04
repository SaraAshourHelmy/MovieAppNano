package com.game.movieappNano.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.game.movieappNano.R;
import com.game.movieappNano.models.Movie;
import com.game.movieappNano.network.utilities.VolleyResponse;
import com.game.movieappNano.network.volley.APIRequest;
import com.game.movieappNano.utilities.Constant;
import com.game.movieappNano.utilities.DialogUtility;
import com.game.movieappNano.views.activity.MovieDetailsActivity;
import com.game.movieappNano.views.adapter.MovieAdapter;

import java.util.ArrayList;


public class MovieFragment extends Fragment implements VolleyResponse,
        MovieAdapter.RecyclerItemClick {

    private RecyclerView mRecyclerMovie;
    public static final String MOVIE_TITLe = "tile";
    public static final String IMAGE_URL = "imgURL";
    public static final String OVERVIEW = "overview";
    public static final String VOTE_AVERAGE = "voteAverage";
    public static final String RELEASE_DATE = "releaseDate";

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerMovie = (RecyclerView) view.findViewById(R.id.rv_movies);
        getMovies(Constant.movieType.POPULAR.name().toLowerCase());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.sort_movie, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String url;
        DialogUtility.showProgressDialog(getContext());
        switch (item.getItemId()) {
            case R.id.menu_popular:

                getMovies(Constant.movieType.POPULAR.name().toLowerCase());

                break;
            case R.id.menu_rated:

                getMovies(Constant.movieType.TOP_RATED.name().toLowerCase());
                break;
            default:
                break;
        }
        return true;
    }

    public void getMovies(String sortType) {
        String url = Constant.getURL
                (sortType);
        APIRequest.getMovie(getContext(), url, MovieFragment.this);
    }

    @Override
    public void onResponse(ArrayList<Movie> movies) {

        DialogUtility.dismissProgressDialog();
        // set up recycler view adapter
        MovieAdapter adapter = new MovieAdapter(movies, MovieFragment.this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerMovie.setLayoutManager(layoutManager);
        mRecyclerMovie.setAdapter(adapter);
    }

    @Override
    public void onError(String errorMsg) {
        DialogUtility.dismissProgressDialog();
        DialogUtility.showMessageDialog(getContext(), errorMsg);
    }

    @Override
    public void onItemClick(Movie movie) {

        Intent moveToDetailsIntent = new Intent(getContext(), MovieDetailsActivity.class);
        moveToDetailsIntent.putExtra(MOVIE_TITLe, movie.getOriginalTitle());
        moveToDetailsIntent.putExtra(IMAGE_URL, movie.getImgURL());
        moveToDetailsIntent.putExtra(OVERVIEW, movie.getOverview());
        moveToDetailsIntent.putExtra(VOTE_AVERAGE, movie.getVoteAverage());
        moveToDetailsIntent.putExtra(RELEASE_DATE, movie.getReleaseDate());
        startActivity(moveToDetailsIntent);

    }
}
