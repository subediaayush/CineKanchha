package com.cinekancha.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;

import butterknife.BindView;
import io.reactivex.annotations.Nullable;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class TrollHolder extends HomeItemHolder {
    @Nullable
    @BindView(R.id.troll)
    public ImageView troll;
    @BindView(R.id.txtViewAll)
    public TextView txtViewAll;

    public TrollHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        view.setOnClickListener(this);
    }
}
