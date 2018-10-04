package com.cinekancha.movies;

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

public class MovieHolder extends HomeItemHolder {

    @BindView(R.id.txtMovie)
    public TextView txtMovie;
    @BindView(R.id.txtDate)
    public TextView txtDate;
    @BindView(R.id.imgMovie)
    public ImageView imgMovie;

    public MovieHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view, OnClickListener listener) {
        super(baseRecyclerAdapter, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(getAdapterPosition());
            }
        });
    }
}
