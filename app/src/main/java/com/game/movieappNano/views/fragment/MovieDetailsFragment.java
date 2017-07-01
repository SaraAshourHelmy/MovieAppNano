package com.game.movieappNano.views.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.game.movieappNano.R;
import com.game.movieappNano.db.FavoriteMovieContract;
import com.game.movieappNano.models.Movie;
import com.game.movieappNano.models.Review;
import com.game.movieappNano.models.Trailer;
import com.game.movieappNano.network.utilities.NetworkAPIs;
import com.game.movieappNano.network.utilities.VolleyResponse;
import com.game.movieappNano.utilities.Constant;
import com.game.movieappNano.utilities.DialogUtility;
import com.game.movieappNano.utilities.IntentUtility;
import com.game.movieappNano.views.adapter.ReviewAdapter;
import com.game.movieappNano.views.adapter.TrailerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sara on 6/3/2017.
 */

public class MovieDetailsFragment extends Fragment implements TrailerAdapter.RecyclerItemClick {

    private TextView tv_title;
    private TextView tv_overview;
    private TextView tv_voteAverage;
    private TextView tv_releaseDate;
    private ImageView imgV_poster;
    private static String title, overview, vote, releaseDate, imgURL, movieID;
    private RecyclerView recycler_trailers, recycler_reviews;
    private ProgressBar progress_loading;
    private LinearLayout lnr_trailer, lnr_review;
    private ToggleButton btn_fav;
    private Movie movie;
    public static final String STATE_FIRST = "state";
    private boolean first = true;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private static ArrayList<Trailer> lstTrailers;
    private static ArrayList<Review> lstReviews;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_movie_details
                , container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_overview = (TextView) view.findViewById(R.id.tv_movieOverview);
        tv_voteAverage = (TextView) view.findViewById(R.id.tv_movieVote);
        tv_releaseDate = (TextView) view.findViewById(R.id.tv_movieReleaseDate);
        tv_title = (TextView) view.findViewById(R.id.tv_movieTitle);
        imgV_poster = (ImageView) view.findViewById(R.id.imgV_detailsPoster);
        recycler_trailers = (RecyclerView) view.findViewById(R.id.recycler_trailers);
        recycler_reviews = (RecyclerView) view.findViewById(R.id.recycler_reviews);
        progress_loading = (ProgressBar) view.findViewById(R.id.progress_loading);
        lnr_trailer = (LinearLayout) view.findViewById(R.id.lnr_trailer);
        lnr_review = (LinearLayout) view.findViewById(R.id.lnr_review);
        btn_fav = (ToggleButton) view.findViewById(R.id.btn_fav);

        tv_title.setText(title);
        tv_overview.setText(overview);
        tv_voteAverage.setText(vote);
        tv_releaseDate.setText(releaseDate);

        Picasso.with(getContext()).load(imgURL).placeholder(R.drawable.loading)
                .into(imgV_poster);

        getTrailer();
        getReviews();
        checkFavorite();
        btn_fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                setFavorite(isChecked);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Intent intent = getActivity().getIntent();
            if (intent.hasExtra(MovieFragment.MOVIE)) {
                movie = intent.getParcelableExtra(MovieFragment.MOVIE);
                movieID = movie.getId();
                title = movie.getOriginalTitle();
                overview = movie.getOverview();
                vote = movie.getVoteAverage();
                releaseDate = movie.getReleaseDate();
                imgURL = movie.getImgURL();
            }
        } else {
            first = savedInstanceState.getBoolean(STATE_FIRST);
        }
    }

    public void getTrailer() {

        if (first) {
            progress_loading.setVisibility(View.VISIBLE);
            String type = Constant.movieInfo.VIDEOS.name().toLowerCase();
            String url = Constant.getMovieInfoURL(movieID, type);
            NetworkAPIs.getTrailers(getContext(), url, new VolleyResponse.TrailerResponse() {

                @Override
                public void onResponse(ArrayList<Trailer> trailers) {

                    progress_loading.setVisibility(View.GONE);
                    lstTrailers = trailers;
                    setTrailerList(trailers);
                }

                @Override
                public void onError(String errorMsg) {
                    progress_loading.setVisibility(View.GONE);
                    DialogUtility.showMessageDialog(getContext(), errorMsg);
                }
            });
        } else
            setTrailerList(lstTrailers);
    }

    private void setTrailerList(ArrayList<Trailer> trailerList) {
        if (trailerList.size() > 0) {
            lnr_trailer.setVisibility(View.VISIBLE);
            trailerAdapter = new TrailerAdapter(trailerList, MovieDetailsFragment.this);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recycler_trailers.setLayoutManager(manager);
            recycler_trailers.setAdapter(trailerAdapter);
        }

    }

    @Override
    public void onItemClick(String trailerKey) {

        IntentUtility.openVideo(getActivity(), trailerKey);
    }

    public void getReviews() {

        if (first) {
            progress_loading.setVisibility(View.VISIBLE);
            String type = Constant.movieInfo.REVIEWS.name().toLowerCase();
            String url = Constant.getMovieInfoURL(movieID, type);
            NetworkAPIs.getReviews(getContext(), url, new VolleyResponse.ReviewsResponse() {

                @Override
                public void onResponse(ArrayList<Review> reviews) {

                    progress_loading.setVisibility(View.GONE);
                    lstReviews = reviews;
                    setReviewList(reviews);
                }

                @Override
                public void onError(String errorMsg) {
                    progress_loading.setVisibility(View.GONE);
                    DialogUtility.showMessageDialog(getContext(), errorMsg);
                }
            });

        } else
            setReviewList(lstReviews);
    }

    private void setReviewList(ArrayList<Review> reviewList) {
        if (reviewList.size() > 0) {
            lnr_review.setVisibility(View.VISIBLE);
            reviewAdapter = new ReviewAdapter(reviewList);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recycler_reviews.setLayoutManager(manager);
            recycler_reviews.setAdapter(reviewAdapter);
        }

    }

    private void setFavorite(boolean check) {
        ContentResolver resolver = getContext().getContentResolver();
        try {
            if (check) {
                ContentValues cv = new ContentValues();
                cv.put(FavoriteMovieContract.FavoriteMovieEntry._ID, movieID);
                cv.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_IMAGE_URL, imgURL);
                cv.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_OVERVIEW, overview);
                cv.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_RELEASE_DATE, releaseDate);
                cv.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_VOTE, vote);
                cv.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_TITLE, title);
                Uri uri = resolver.insert(FavoriteMovieContract.FavoriteMovieEntry.FAVORITE_URI,
                        cv);
                Log.e("insert", uri.toString());
                Toast.makeText(getContext(), "Favorite", Toast.LENGTH_SHORT).show();
            } else {
                Uri uri = FavoriteMovieContract.FavoriteMovieEntry.FAVORITE_URI
                        .buildUpon().appendPath(movieID).build();
                int rowNo = resolver.delete(uri, null, null);
                if (rowNo > 0)
                    Toast.makeText(getContext(), "UnFavorite", Toast.LENGTH_SHORT).show();

            }
            setFavIcon(check);
        } catch (Exception e) {
            Log.e("error_exception", e.getMessage());
        }
    }

    private void setFavIcon(boolean check) {
        if (check) {
            btn_fav.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_fav));
            btn_fav.setChecked(true);
        } else {
            btn_fav.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_unfav));
            btn_fav.setChecked(false);
        }
    }

    private void checkFavorite() {

        ContentResolver resolver = getContext().getContentResolver();
        Cursor cursor = resolver.query(FavoriteMovieContract.FavoriteMovieEntry.FAVORITE_URI,
                null, FavoriteMovieContract.FavoriteMovieEntry._ID + " = " + movieID,
                null, null);
        if (cursor.getCount() > 0)
            setFavIcon(true);
        else
            setFavIcon(false);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_FIRST, false);
    }
}
