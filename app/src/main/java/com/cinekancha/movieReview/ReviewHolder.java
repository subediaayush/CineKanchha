package com.cinekancha.movieReview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.home.HomeItemHolder;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class ReviewHolder extends HomeItemHolder {

//	@BindView(R.id.title)
//	public TextView title;

    @BindView(R.id.imgFeature)
    public ImageView imgFeature;

    @BindView(R.id.txtTitle)
    public TextView txtTitle;

    @BindView(R.id.txtSubTitle)
    public TextView txtSubTitle;

    public ReviewHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
    }
}
