package com.cinekancha.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.view.FadingTextView;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class TopStoryHolder extends HomeItemHolder {
    @BindView(R.id.txtTopStories)
    public FadingTextView txtTopStories;

    @BindView(R.id.imgArrow)
    public ImageView imgArrow;

    public TopStoryHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
    }
}
