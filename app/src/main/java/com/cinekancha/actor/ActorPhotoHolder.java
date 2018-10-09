package com.cinekancha.actor;

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

public class ActorPhotoHolder extends HomeItemHolder {

    @BindView(R.id.imgActorPic)
    public ImageView imgActorPic;

    public ActorPhotoHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view, OnClickListener listener) {
        super(baseRecyclerAdapter, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(getAdapterPosition());
            }
        });
    }
}
