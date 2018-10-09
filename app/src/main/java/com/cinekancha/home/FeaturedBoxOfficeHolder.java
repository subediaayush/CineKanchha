package com.cinekancha.home;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.boxOffice.BoxOfficeActivity;
import com.cinekancha.entities.model.BoxOfficeItem;
import com.cinekancha.utils.GlobalUtils;

import java.util.List;

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
            GlobalUtils.navigateActivity(view.getContext(), true, BoxOfficeActivity.class);
        });

        adapter = new FeatureBoxOfficeAdapter();
        Drawable divider = ContextCompat.getDrawable(view.getContext(), R.drawable.list_divider);
        DividerItemDecoration decor = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        decor.setDrawable(divider);
        boxOffice.addItemDecoration(decor);
        boxOffice.setAdapter(adapter);
    }

    public void setBoxOffice(List<BoxOfficeItem> items) {
        adapter.setBoxOfficeItems(items);
    }
}
