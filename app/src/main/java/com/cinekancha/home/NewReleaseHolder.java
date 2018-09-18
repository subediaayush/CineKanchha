package com.cinekancha.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.entities.ThumbWrapper;
import com.cinekancha.entities.ThumbnailConverter;
import com.cinekancha.entities.model.Movie;

import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class NewReleaseHolder extends HomeItemHolder {

    @BindView(R.id.list)
    public RecyclerView newMoviesList;
    @BindView(R.id.label)
    public TextView title;
    private ThumbnailAdapter<Movie> adapter;

    public NewReleaseHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        setIsRecyclable(false);
        adapter = new ThumbnailAdapter<>(R.layout.layout_featued_movie_new_release, new ThumbnailConverter<Movie>() {
            @Override
            public ThumbWrapper convert(Movie data) {
                return new ThumbWrapper(
                        data.getFeaturedImage(),
                        data.getName()
                );
            }
        });
        newMoviesList.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        newMoviesList.setNestedScrollingEnabled(false);
        newMoviesList.setLayoutManager(manager);
    }

    public void setMovies(List<Movie> movies) {
        adapter.setThumbnails(movies);
    }
}
