package com.game.movieappNano.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.movieappNano.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Sara on 6/3/2017.
 */

public class MovieDetailsFragment extends Fragment {

    private TextView tv_title;
    private TextView tv_overview;
    private TextView tv_voteAverage;
    private TextView tv_releaseDate;
    private ImageView imgV_poster;
    private String title, overview, vote, releaseDate, imgURL;

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

        tv_title.setText(title);
        tv_overview.setText(overview);
        tv_voteAverage.setText(vote);
        tv_releaseDate.setText(releaseDate);

        Picasso.with(getContext()).load(imgURL).placeholder(R.drawable.loading)
                .into(imgV_poster);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity()
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(MovieFragment.MOVIE_TITLe))
            title = intent.getStringExtra(MovieFragment.MOVIE_TITLe);

        if (intent.hasExtra(MovieFragment.OVERVIEW))
            overview = intent.getStringExtra(MovieFragment.OVERVIEW);

        if (intent.hasExtra(MovieFragment.VOTE_AVERAGE))
            vote = intent.getStringExtra(MovieFragment.VOTE_AVERAGE);

        if (intent.hasExtra(MovieFragment.RELEASE_DATE))
            releaseDate = intent.getStringExtra(MovieFragment.RELEASE_DATE);

        if (intent.hasExtra(MovieFragment.IMAGE_URL))
            imgURL = intent.getStringExtra(MovieFragment.IMAGE_URL);
    }
}
