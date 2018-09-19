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
import com.cinekancha.entities.model.Movie;
import com.cinekancha.listener.OnClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class FeaturedReviewsHolder extends HomeItemHolder {

    private final ThumbnailAdapter<Movie> adapter;
    @BindView(R.id.list)
    public RecyclerView upcomingMoviesList;
    @BindView(R.id.label)
    public TextView title;

    public FeaturedReviewsHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        setIsRecyclable(false);
        adapter = new ThumbnailAdapter<>(R.layout.layout_featured_review, new ThumbnailConverter<Movie>() {
            @Override
            public ThumbWrapper convert(Movie data) {
                return new ThumbWrapper(
                        data.getFeaturedImage(),
                        "Movie Review",
                        data.getName(), data.getId()
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

        title.setText("Movie Reviews");
    }

    public void setMovies(List<Movie> movies) {
        adapter.setThumbnails(movies);
    }

}
