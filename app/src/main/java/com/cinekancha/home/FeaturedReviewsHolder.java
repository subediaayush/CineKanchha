package com.cinekancha.home;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.entities.ThumbWrapper;
import com.cinekancha.entities.ThumbnailConverter;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.ReviewData;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movieReview.ReviewDetailActivity;
import com.cinekancha.movieReview.ReviewListActivity;
import com.cinekancha.utils.EqualSpacingItemDecoration;
import com.cinekancha.utils.GlobalUtils;

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
    @BindView(R.id.txtViewAll)
    public TextView txtViewAll;

    public List<Movie> movieList;


    public FeaturedReviewsHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        setIsRecyclable(false);
        txtViewAll.setVisibility(View.VISIBLE);
        txtViewAll.setOnClickListener(view1 -> GlobalUtils.navigateActivity(itemView.getContext(), false, ReviewListActivity.class));
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
                Movie movie = movieList.get(id);
                ReviewData reviewData = new ReviewData();
                reviewData.setId(movie.getId());
                reviewData.setFeaturedImage(movie.getFeaturedImage());
                reviewData.setName(movie.getName());
                reviewData.setReview(movie.getReview());
                Intent intent = new Intent(itemView.getContext(), ReviewDetailActivity.class);
                intent.putExtra("review", (Parcelable) reviewData);
                itemView.getContext().startActivity(intent);
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
        title.setText("Movie Reviews");
    }

    public void setMovies(List<Movie> movies) {
        this.movieList = movies;
        adapter.setThumbnails(movies);
    }

}
