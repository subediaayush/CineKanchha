package com.cinekancha.trending;

import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.Video;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class TrendingAdapter extends BaseRecyclerAdapter<TrendingHolder> {
    private List<Video> trendingList = new ArrayList<>();
    private OnClickListener listener;

    public TrendingAdapter(List<Video> trendingList, OnClickListener listener) {
        this.trendingList = trendingList;
        this.listener = listener;
    }

    public TrendingAdapter(OnClickListener listener) {
        this.listener = listener;
    }

    public void setTrendingList(List<Video> videoList) {
        this.trendingList.clear();
        this.trendingList.addAll(videoList);
        notifyDataSetChanged();
    }

    public void addTrendingList(List<Video> videoList) {
        this.trendingList.addAll(videoList);
        notifyDataSetChanged();
    }

    @Override
    public TrendingHolder onCreateView(int viewType, View view) {
        return new TrendingHolder(this, view, listener);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.adapter_trending
        };
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        TrendingHolder holder = (TrendingHolder) baseHolder;
        Video movie = trendingList.get(position);
        holder.txtMovie.setText(movie.getName());
        Picasso.with(holder.itemView.getContext()).load(Constants.imageUrl + movie.getImageUrl()).into(holder.imgTrending);
        holder.imgTrending.setOnClickListener(view -> {
            listener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return trendingList.size();
    }
}
