package com.cinekancha.newsGossips;

import android.support.annotation.Nullable;
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

public class NewsGossipHolder extends HomeItemHolder {

    @BindView(R.id.imgNewsGossip)
    public ImageView imgNewsGossip;
    @BindView(R.id.txtNewsGossip)
    public TextView txtNewsGossip;
    @Nullable
    @BindView(R.id.divider)
    public View divider;

    public NewsGossipHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        view.setOnClickListener(this);
    }

    @Override
    public int[] getClickViewIdList() {
        return new int[]{R.id.holder};
    }
}
