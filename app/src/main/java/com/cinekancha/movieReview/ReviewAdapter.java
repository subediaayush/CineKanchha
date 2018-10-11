package com.cinekancha.movieReview;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.ReviewData;
import com.cinekancha.entities.model.Reviews;
import com.cinekancha.entities.model.TriviaData;
import com.cinekancha.home.TriviaHolder;
import com.cinekancha.utils.Constants;
import com.cinekancha.utils.GradientGenartor;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class ReviewAdapter extends BaseRecyclerAdapter<ReviewHolder> {
    private List<ReviewData> mData;

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
        holder.txtSubTitle.setText(reviewData.getReview());
        Picasso.with(holder.itemView.getContext()).load(Constants.imageUrl + reviewData.getFeaturedImage()).placeholder(R.drawable.placeholder_movie).into(holder.imgFeature);

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setTrivias(List<ReviewData> triviaData) {
        this.mData = triviaData;
        notifyDataSetChanged();
    }

    public void addTrivias(@NonNull List<ReviewData> triviaData) {
        if (this.mData == null) this.mData = new ArrayList<>();
        int initial = this.mData.size();
        this.mData.addAll(triviaData);
        notifyItemRangeInserted(initial, triviaData.size());
    }

    public ReviewData getTrivia(int position) {
        return mData.get(position % mData.size());
    }
}
