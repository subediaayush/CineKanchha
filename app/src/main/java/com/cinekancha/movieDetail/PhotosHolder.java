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

public class PhotosHolder extends HomeItemHolder {

    @BindView(R.id.imgPhoto)
    public ImageView imgPhoto;


    public PhotosHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        view.setOnClickListener(this);
    }
}
