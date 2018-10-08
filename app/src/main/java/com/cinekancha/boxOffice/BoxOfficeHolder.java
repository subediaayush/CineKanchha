package com.cinekancha.boxOffice;

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

public class BoxOfficeHolder extends HomeItemHolder {

    @BindView(R.id.txtMovieName)
    public TextView txtMovieName;
    @BindView(R.id.txtWeekend)
    public TextView txtWeekend;
    @BindView(R.id.txtGross)
    public TextView txtGross;
    @BindView(R.id.txtWeek)
    public TextView txtWeek;
    @BindView(R.id.imgBoxOffice)
    public ImageView imgBoxOffice;

    public BoxOfficeHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view, OnClickListener listener) {
        super(baseRecyclerAdapter, view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(getAdapterPosition());
            }
        });
    }
}
