package com.game.movieappNano.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.movieappNano.R;
import com.game.movieappNano.models.Movie;
import com.game.movieappNano.models.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sara on 6/3/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.Holder> {

    ArrayList<Trailer> lstTrailer;
    RecyclerItemClick recyclerItemClick;

    public TrailerAdapter(ArrayList<Trailer> lstTrailer,
                          RecyclerItemClick recyclerItemClick) {
        this.lstTrailer = lstTrailer;
        this.recyclerItemClick = recyclerItemClick;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_trailer, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        String trailerName = lstTrailer.get(position).getName();
        holder.tv_title.setText(trailerName);
    }

    @Override
    public int getItemCount() {
        return lstTrailer.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_title;
        View itemView;

        public Holder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_title = (TextView) itemView.findViewById(R.id.tv_trailerTitle);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            recyclerItemClick.onItemClick(lstTrailer.get(getAdapterPosition()).getKey());
        }
    }

    public interface RecyclerItemClick {
        void onItemClick(String trailerKey);

    }
}
