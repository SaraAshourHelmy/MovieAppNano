package com.game.movieappNano.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.game.movieappNano.R;
import com.game.movieappNano.models.Review;
import com.game.movieappNano.models.Trailer;

import java.util.ArrayList;

/**
 * Created by Sara on 6/3/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Holder> {

    ArrayList<Review> lstReviews;

    public ReviewAdapter(ArrayList<Review> lstReviews) {
        this.lstReviews = lstReviews;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_review, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        String author = lstReviews.get(position).getAuthor();
        String content = lstReviews.get(position).getContent();
        holder.tv_author.setText(author);
        holder.tv_content.setText(content);
    }

    @Override
    public int getItemCount() {
        return lstReviews.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView tv_author, tv_content;


        public Holder(View itemView) {
            super(itemView);
            tv_author = (TextView) itemView.findViewById(R.id.tv_review_author);
            tv_content = (TextView) itemView.findViewById(R.id.tv_review_content);
        }


    }

}
