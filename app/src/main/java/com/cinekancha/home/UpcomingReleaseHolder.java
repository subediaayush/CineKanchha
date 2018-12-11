package com.cinekancha.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.entities.ThumbWrapper;
import com.cinekancha.entities.ThumbnailConverter;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movieDetail.MoviePostDetailActivity;
import com.cinekancha.utils.EqualSpacingItemDecoration;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class UpcomingReleaseHolder extends HomeItemHolder {

    private final ThumbnailAdapter<Movie> adapter;
    @BindView(R.id.list)
    public RecyclerView upcomingMoviesList;
    @BindView(R.id.label)
    public TextView title;
    @BindView(R.id.txtViewAll)
    public TextView txtViewAll;

    public List<Movie> movieList;

    public UpcomingReleaseHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
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
        upcomingMoviesList.addItemDecoration(new EqualSpacingItemDecoration(16, EqualSpacingItemDecoration.HORIZONTAL));
    }

    public void setMovies(List<Movie> movies) {

        movieList = movies;
        adapter.setThumbnails(movies);
    }

}
