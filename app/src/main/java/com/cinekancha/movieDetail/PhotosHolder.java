package com.cinekancha.movieDetail;

import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.home.HomeItemHolder;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class RatingHolder extends HomeItemHolder {

    @BindView(R.id.txtRating)
    public TextView txtRating;
    @BindView(R.id.txtFromRating)
    public TextView txtFromRating;

    public RatingHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        view.setOnClickListener(this);
    }
}
