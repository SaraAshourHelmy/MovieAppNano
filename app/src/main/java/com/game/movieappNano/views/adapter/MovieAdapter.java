package com.game.movieappNano.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.game.movieappNano.R;
import com.game.movieappNano.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sara on 6/3/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.Holder> {

    ArrayList<Movie> lstMovies;
    RecyclerItemClick recyclerItemClick;

    public MovieAdapter(ArrayList<Movie> lstMovies, RecyclerItemClick recyclerItemClick) {
        this.lstMovies = lstMovies;
        this.recyclerItemClick = recyclerItemClick;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.adapter_movie, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return lstMovies.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img_movie;
        View itemView;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            img_movie = (ImageView) itemView.findViewById(R.id.imgV_moviePoster);
            img_movie.setOnClickListener(this);
        }

        public void bindData() {
            Picasso.with(img_movie.getContext()).
                    load(lstMovies.get(getAdapterPosition()).getImgURL())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading_error)
                    .into(img_movie);
        }

        @Override
        public void onClick(View v) {

            recyclerItemClick.onItemClick(lstMovies.get(getAdapterPosition()));
        }
    }

    public interface RecyclerItemClick {
        void onItemClick(Movie movie);

    }
}
