package com.cinekancha.movies;

import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.Article;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movieDetail.RatingHolder;
import com.cinekancha.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class MoviesAdapter extends BaseRecyclerAdapter<MovieHolder> {
    private List<Movie> movieList;
    private OnClickListener listener;

    public MoviesAdapter(List<Movie> movieList, OnClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    @Override
    public MovieHolder onCreateView(int viewType, View view) {
        return new MovieHolder(this, view, listener);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.adapter_movie
        };
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        MovieHolder holder = (MovieHolder) baseHolder;
        Movie movie = movieList.get(position);
        holder.txtMovie.setText(movie.getName());
        holder.txtDate.setText(movie.getReleaseDate());
        Picasso.with(holder.itemView.getContext()).load(Constants.imageUrl + movie.getFeaturedImage()).into(holder.imgMovie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
