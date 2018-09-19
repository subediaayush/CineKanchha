package com.cinekancha.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.article.ArticleDetailActivity;
import com.cinekancha.entities.ThumbWrapper;
import com.cinekancha.entities.ThumbnailConverter;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movieDetail.MoviePostDetailActivity;

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
                        data.getName(),
                        data.getId()
                );
            }
        }, id -> {
            Log.d("MovieID", String.valueOf(id));
            Intent detail = new Intent(view.getContext(), MoviePostDetailActivity.class);
            detail.putExtra("movie", String.valueOf(id));
            view.getContext().startActivity(detail);
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
