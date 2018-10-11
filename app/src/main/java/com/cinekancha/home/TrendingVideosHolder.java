package com.cinekancha.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.cinekancha.entities.model.Video;
import com.cinekancha.trending.TrendingActivity;
import com.cinekancha.utils.GlobalUtils;

import java.net.MalformedURLException;
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
    @BindView(R.id.txtViewAll)
    public TextView txtViewAll;
    private String videoId = "";
    private List<Video> trendingList;


    public TrendingVideosHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        setIsRecyclable(false);
        txtViewAll.setVisibility(View.VISIBLE);
        txtViewAll.setOnClickListener(view1 -> {
            GlobalUtils.navigateActivity(view.getContext(), false, TrendingActivity.class);
        });
        adapter = new ThumbnailAdapter<>(R.layout.layout_featured_trending_video, new ThumbnailConverter<Video>() {
            @Override
            public ThumbWrapper convert(Video data) {
                return new ThumbWrapper(
                        data.getImageUrl(),
                        data.getName(),
                        Integer.parseInt(data.getId())
                );
            }
        }, id -> openYouTube(view.getContext(), id));
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

    private void openYouTube(Context context, int id) {
        try {
            videoId = GlobalUtils.extractYoutubeId(trendingList.get(id).getLink());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoId));
        context.startActivity(intent);
    }

    public void setVideos(List<Video> trendingList) {
        this.trendingList = trendingList;
        adapter.setThumbnails(trendingList);
    }

}
