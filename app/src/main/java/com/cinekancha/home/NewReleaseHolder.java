package com.cinekancha.home;

import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.entities.ThumbWrapper;
import com.cinekancha.entities.ThumbnailConverter;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.movieDetail.MoviePostDetailActivity;
import com.cinekancha.utils.EqualSpacingItemDecoration;

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
    public List<Movie> movieList;
    @BindView(R.id.txtViewAll)
    public TextView txtViewAll;

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
        }, position -> {
            Movie movie = movieList.get(position);
            Intent detail = new Intent(view.getContext(), MoviePostDetailActivity.class);
            detail.putExtra("movie", String.valueOf(movie.getId()));
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
        newMoviesList.addItemDecoration(new EqualSpacingItemDecoration(16, EqualSpacingItemDecoration.HORIZONTAL));

    }

    public void setMovies(List<Movie> movies) {
        movieList = movies;
        adapter.setThumbnails(movies);
    }
}
