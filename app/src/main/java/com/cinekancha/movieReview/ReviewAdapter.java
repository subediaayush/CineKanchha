package com.cinekancha.movieReview;

import android.text.Html;
import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.ReviewData;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class ReviewAdapter extends BaseRecyclerAdapter<ReviewHolder> {
    private List<ReviewData> mData = new ArrayList<>();
    private OnClickListener listener;

    public ReviewAdapter(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ReviewHolder onCreateView(int viewType, View view) {
        return new ReviewHolder(this, view);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.adapter_review
        };
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        ReviewHolder holder = (ReviewHolder) baseHolder;
        ReviewData reviewData = mData.get(position);
        holder.txtTitle.setText(reviewData.getName());
        holder.txtSubTitle.setText(Html.fromHtml(reviewData.getReview()));
        Picasso.with(holder.itemView.getContext()).load(Constants.imageUrl + reviewData.getFeaturedImage()).placeholder(R.drawable.placeholder_movie).into(holder.imgFeature);
        holder.itemView.setOnClickListener(view -> {
            listener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setReviewList(List<ReviewData> reviewList) {
        this.mData.clear();
        this.mData.addAll(reviewList);
        notifyDataSetChanged();
    }

    public void addReviewList(List<ReviewData> reviewList) {
        this.mData.addAll(reviewList);
        notifyDataSetChanged();
    }

    public ReviewData getTrivia(int position) {
        return mData.get(position % mData.size());
    }
}
