package com.cinekancha.home;

import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.boxOffice.BoxOfficeActivity;
import com.cinekancha.entities.model.MovieBoxOffice;
import com.cinekancha.utils.GlobalUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

class FeaturedBoxOfficeHolder extends HomeItemHolder {

    @BindView(R.id.movies)
    public RecyclerView boxOffice;

    @BindView(R.id.txtViewAll)
    public TextView txtViewAll;

    private FeatureBoxOfficeAdapter adapter;

    public FeaturedBoxOfficeHolder(HomeDataAdapter homeDataAdapter, View view) {
        super(homeDataAdapter, view);
        view.setOnClickListener(this);
        txtViewAll.setVisibility(View.VISIBLE);

        boxOffice.setLayoutManager(new LinearLayoutManager(view.getContext()) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        txtViewAll.setOnClickListener(view1 -> {
            GlobalUtils.navigateActivity(view.getContext(), false, BoxOfficeActivity.class);
        });

        adapter = new FeatureBoxOfficeAdapter();
        boxOffice.setAdapter(adapter);
        boxOffice.setNestedScrollingEnabled(false);
    }

    public void setBoxOffice(List<MovieBoxOffice> items) {
        adapter.setBoxOfficeItems(items);
    }
}
