package com.cinekancha.movieDetail;

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

public class ArticleHolder extends HomeItemHolder {
    @BindView(R.id.txtMovieName)
    public TextView txtMovieName;

    @BindView(R.id.txtSummary)
    public TextView txtSummary;

    @BindView(R.id.imgArticle)
    public ImageView imgArticle;

    public ArticleHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        view.setOnClickListener(this);
    }
}
