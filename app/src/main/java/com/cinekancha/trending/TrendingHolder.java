package com.cinekancha.trending;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.home.HomeItemHolder;
import com.cinekancha.listener.OnClickListener;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class TrendingHolder extends HomeItemHolder {

    @BindView(R.id.txtMovie)
    public TextView txtMovie;
    @BindView(R.id.txtSubTitle)
    public TextView txtSubTitle;
    @BindView(R.id.imgTrending)
    public ImageView imgTrending;
    @BindView(R.id.imgMore)
    public ImageView imgMore;

    public TrendingHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view, OnClickListener listener) {
        super(baseRecyclerAdapter, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(getAdapterPosition());
            }
        });
    }
}
