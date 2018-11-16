package com.cinekancha.home;

import android.content.Context;
import android.content.Intent;
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
import com.cinekancha.movieDetail.MoviePostDetailActivity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class AllMoviesHolder extends HomeItemHolder {

    private final ThumbnailAdapter<Movie> adapter;
    @BindView(R.id.list)
    public RecyclerView allMoviesList;
    @BindView(R.id.label)
    public TextView title;
    @BindView(R.id.txtViewAll)
    public TextView txtViewAll;

    public List<Movie> movieList;

    public AllMoviesHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        setIsRecyclable(false);
        adapter = new ThumbnailAdapter<>(R.layout.layout_featured_movie, new ThumbnailConverter<Movie>() {
            @Override
            public ThumbWrapper convert(Movie data) {
                return new ThumbWrapper(
                        data.getFeaturedImage(),
                        data.getName(),
                        data.getReleaseDate(), data.getId()
                );
            }
        }, new OnClickListener() {
            @Override
            public void onClick(int position) {
                Movie movie = movieList.get(position);
                Intent detail = new Intent(view.getContext(), MoviePostDetailActivity.class);
                detail.putExtra("movie", String.valueOf(movie.getId()));
                view.getContext().startActivity(detail);
            }
        });
        allMoviesList.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        Context context = view.getContext();
        allMoviesList.setNestedScrollingEnabled(false);
        allMoviesList.setLayoutManager(manager);
        DividerItemDecoration decoration = new DividerItemDecoration(allMoviesList.getContext(), DividerItemDecoration.HORIZONTAL);
        decoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_transparent));
        allMoviesList.addItemDecoration(decoration);
    }

    public void setMovies(List<Movie> movies) {

        movieList = movies;
        adapter.setThumbnails(movies);
    }

}
