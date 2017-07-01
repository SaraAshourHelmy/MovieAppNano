package com.game.movieappNano.views.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.game.movieappNano.R;
import com.game.movieappNano.db.FavoriteMovieContract;
import com.game.movieappNano.models.Movie;
import com.game.movieappNano.network.utilities.NetworkAPIs;
import com.game.movieappNano.network.utilities.VolleyResponse;
import com.game.movieappNano.network.volley.NetworkRequest;
import com.game.movieappNano.utilities.Constant;
import com.game.movieappNano.utilities.DialogUtility;
import com.game.movieappNano.views.activity.MovieDetailsActivity;
import com.game.movieappNano.views.adapter.MovieAdapter;

import java.util.ArrayList;


public class MovieFragment extends Fragment implements
        VolleyResponse.MovieResponse,
        MovieAdapter.RecyclerItemClick, LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mRecyclerMovie;
    private TextView tv_no_data;
    public static final String MOVIE = "movie";
    public static final int LOADER_ID = 300;
    public String state = Constant.movieType.POPULAR.toString().toLowerCase();
    public static final String STATE_MOVIE = "state";
    public static final String STATE_LIST = "state_list";
    public static final String STATE_First = "first";
    private GridLayoutManager layoutManager;
    private Parcelable mListState;
    public static ArrayList<Movie> lstMovie;
    private boolean first = true, isLand;


    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        isLand = getResources().getBoolean(R.bool.isLand);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(STATE_LIST))
                mListState = savedInstanceState.getParcelable(STATE_LIST);
            first = savedInstanceState.getBoolean(STATE_First);
        }
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
        tv_no_data = (TextView) view.findViewById(R.id.tv_no_data);

        if (first)
            getMovies(state);
        else
            setMovieRecycle(lstMovie);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.sort_movie, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        DialogUtility.showProgressDialog(getContext());
        switch (item.getItemId()) {
            case R.id.menu_popular:
                state = Constant.movieType.POPULAR.name().toLowerCase();
                getMovies(Constant.movieType.POPULAR.name().toLowerCase());

                break;
            case R.id.menu_rated:
                state = Constant.movieType.TOP_RATED.name().toLowerCase();
                getMovies(Constant.movieType.TOP_RATED.name().toLowerCase());
                break;

            case R.id.menu_favorite:
                state = Constant.movieType.Favorite.name().toLowerCase();
                getFavorite();
                break;
            default:
                break;
        }
        return true;
    }

    public void getFavorite() {

        LoaderManager manager = getLoaderManager();
        Loader<Cursor> loader = manager.getLoader(LOADER_ID);
        if (loader == null)
            manager.initLoader(LOADER_ID, null, this);
        else
            manager.restartLoader(LOADER_ID, null, this);

    }

    public void getMovies(String sortType) {
        String url = Constant.getURL
                (sortType);
        NetworkAPIs.getMovies(getContext(), url, MovieFragment.this);
    }

    @Override
    public void onResponse(ArrayList<Movie> movies) {

        DialogUtility.dismissProgressDialog();
        lstMovie = movies;
        setMovieRecycle(movies);

    }


    private void setMovieRecycle(ArrayList<Movie> movies) {

        if (movies != null && movies.size() > 0) {
            tv_no_data.setVisibility(View.GONE);
            // set up recycler view adapter
            MovieAdapter adapter = new MovieAdapter(movies, MovieFragment.this);
            if (isLand)
                layoutManager = new GridLayoutManager(getContext(), 4);
            else
                layoutManager = new GridLayoutManager(getContext(), 3);
            if (mListState != null)
                layoutManager.onRestoreInstanceState(mListState);
            mRecyclerMovie.setLayoutManager(layoutManager);
            mRecyclerMovie.setAdapter(adapter);
        }

    }

    @Override
    public void onError(String errorMsg) {
        DialogUtility.dismissProgressDialog();
        DialogUtility.showMessageDialog(getContext(), errorMsg);
    }

    @Override
    public void onItemClick(Movie movie) {

        Intent moveToDetailsIntent = new Intent(getContext(), MovieDetailsActivity.class);
        moveToDetailsIntent.putExtra(MOVIE, movie);
        startActivity(moveToDetailsIntent);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), FavoriteMovieContract.FavoriteMovieEntry.FAVORITE_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!state.equals(Constant.movieType.Favorite.name().toString().toLowerCase()))
            getLoaderManager().destroyLoader(LOADER_ID);
        else {
            ArrayList<Movie> lstMovies = new ArrayList<>();
            Movie movie;
            while (data.moveToNext()) {
                movie = new Movie();
                movie.setId(data.getString(data.getColumnIndex(FavoriteMovieContract.FavoriteMovieEntry._ID)));
                movie.setOriginalTitle(data.getString(data.getColumnIndex(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_TITLE)));
                movie.setOverview(data.getString(data.getColumnIndex(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_OVERVIEW)));
                movie.setImgURL(data.getString(data.getColumnIndex(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_IMAGE_URL)));
                movie.setReleaseDate(data.getString(data.getColumnIndex(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_RELEASE_DATE)));
                movie.setVoteAverage(data.getString(data.getColumnIndex(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_VOTE)));
                lstMovies.add(movie);
            }
            DialogUtility.dismissProgressDialog();
            if (lstMovies.size() == 0)
                tv_no_data.setVisibility(View.VISIBLE);
            this.lstMovie = lstMovies;
            setMovieRecycle(lstMovies);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_MOVIE, state);
        outState.putBoolean(STATE_First, false);
        if (layoutManager != null) {
            mListState = layoutManager.onSaveInstanceState();
            outState.putParcelable(STATE_LIST, mListState);
        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

}
