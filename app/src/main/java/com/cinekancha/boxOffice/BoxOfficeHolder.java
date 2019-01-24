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
    
    @BindView(R.id.imgBoxOffice)
    public ImageView imgBoxOffice;
    @BindView(R.id.txtMovieName)
    public TextView txtMovieName;
    @BindView(R.id.totalLabel)
    public TextView totalLabel;
    @BindView(R.id.oDayLabel)
    public TextView oDayLabel;
    @BindView(R.id.oWeekLabel)
    public TextView oWeekLabel;
    @BindView(R.id.total)
    public TextView total;
    @BindView(R.id.oDay)
    public TextView oDay;
    @BindView(R.id.oWeek)
    public TextView oWeek;
    @BindView(R.id.oVerdict)
    public TextView oVerdict;
    

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
