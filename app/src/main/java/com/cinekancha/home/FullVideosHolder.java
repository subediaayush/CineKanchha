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
import com.cinekancha.trending.FullMoviesActivity;
import com.cinekancha.utils.EqualSpacingItemDecoration;
import com.cinekancha.utils.GlobalUtils;

import java.net.MalformedURLException;
import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class FullVideosHolder extends HomeItemHolder {

    private final ThumbnailAdapter<Video> adapter;
    @BindView(R.id.list)
    public RecyclerView upcomingMoviesList;
    @BindView(R.id.label)
    public TextView title;
    @BindView(R.id.txtViewAll)
    public TextView txtViewAll;
    private String videoId;
    private List<Video> fullMovie;

    public FullVideosHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        setIsRecyclable(false);
        txtViewAll.setVisibility(View.VISIBLE);
        txtViewAll.setOnClickListener(view1 -> {
            GlobalUtils.navigateActivity(view.getContext(), false, FullMoviesActivity.class);
        });
        adapter = new ThumbnailAdapter<>(R.layout.layout_featured_trending_video, new ThumbnailConverter<Video>() {
            @Override
            public ThumbWrapper convert(Video data) {
                return new ThumbWrapper(
                        data.getImageUrl(),
                        data.getName(), Integer.parseInt(data.getId())
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
        upcomingMoviesList.addItemDecoration(new EqualSpacingItemDecoration(40, EqualSpacingItemDecoration.HORIZONTAL));
        title.setText("Watch Movies");
    }

    private void openYouTube(Context context, int id) {
        try {
            videoId = GlobalUtils.extractYoutubeId(fullMovie.get(id).getLink());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoId));
        context.startActivity(intent);
    }

    public void setVideos(List<Video> movies) {
        this.fullMovie = movies;
        adapter.setThumbnails(movies);
    }

}
