package com.cinekancha.home;

import android.content.Context;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.entities.model.GalleryItem;
import com.cinekancha.entities.ThumbWrapper;
import com.cinekancha.entities.ThumbnailConverter;
import com.cinekancha.movieDetail.MoviePostDetailActivity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class FeaturedTrendingHolder extends HomeItemHolder {

    private final ThumbnailAdapter<GalleryItem> adapter;
    @BindView(R.id.list)
    public RecyclerView upcomingMoviesList;
    @BindView(R.id.label)
    public TextView title;

    public FeaturedTrendingHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        setIsRecyclable(false);
        adapter = new ThumbnailAdapter<>(R.layout.layout_featured_movie, new ThumbnailConverter<GalleryItem>() {
            @Override
            public ThumbWrapper convert(GalleryItem data) {
                return new ThumbWrapper(
                        data.getImageUrl(),
                        data.getName(),
                        (int) (long) data.getId()
                );
            }
        }, id -> {
            Intent detail = new Intent(view.getContext(), MoviePostDetailActivity.class);
            detail.putExtra("movie", id);
            view.getContext().startActivity(detail);
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

        title.setText("Photo Gallery");
    }

    public void setMovies(List<GalleryItem> movies) {
        adapter.setThumbnails(movies);
    }

}
