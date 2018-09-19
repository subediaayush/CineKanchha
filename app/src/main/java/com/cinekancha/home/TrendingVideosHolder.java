package com.cinekancha.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.entities.ThumbWrapper;
import com.cinekancha.entities.ThumbnailConverter;
import com.cinekancha.entities.Video;
import com.cinekancha.listener.OnClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class TrendingVideosHolder extends HomeItemHolder {

    private final ThumbnailAdapter<Video> adapter;
    @BindView(R.id.list)
    public RecyclerView upcomingMoviesList;
    @BindView(R.id.label)
    public TextView title;

    public TrendingVideosHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        setIsRecyclable(false);
        adapter = new ThumbnailAdapter<>(R.layout.layout_featured_trending_video, new ThumbnailConverter<Video>() {
            @Override
            public ThumbWrapper convert(Video data) {
                return new ThumbWrapper(
                        data.getImageUrl(),
                        data.getTitle(),
                        (int) (long) data.getId()
                );
            }
        }, new OnClickListener() {
            @Override
            public void onClick(int id) {

            }
        });
        upcomingMoviesList.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        Context context = view.getContext();
        upcomingMoviesList.setNestedScrollingEnabled(false);
        upcomingMoviesList.setLayoutManager(manager);
        DividerItemDecoration decoration = new DividerItemDecoration(upcomingMoviesList.getContext(), DividerItemDecoration.HORIZONTAL);
        decoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_transparent));
        upcomingMoviesList.addItemDecoration(decoration);

        title.setText("Trending Videos");
    }

    public void setVideos(List<Video> movies) {
        adapter.setThumbnails(movies);
    }

}
