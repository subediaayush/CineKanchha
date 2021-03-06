package com.cinekancha.home;

import android.content.Context;
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
import com.cinekancha.listener.OnClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class FeaturedShowtimesHolder extends HomeItemHolder {

    private final ThumbnailAdapter<GalleryItem> adapter;
    @BindView(R.id.list)
    public RecyclerView upcomingMoviesList;
    @BindView(R.id.label)
    public TextView title;

    public FeaturedShowtimesHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        setIsRecyclable(false);
        adapter = new ThumbnailAdapter<>(R.layout.layout_featured_movie, new ThumbnailConverter<GalleryItem>() {
            @Override
            public ThumbWrapper convert(GalleryItem data) {
                return new ThumbWrapper(
                        data.getImageUrl(),
                        data.getName(), (int) (long) data.getId()
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
    }

    public void setMovies(List<GalleryItem> movies) {
        adapter.setThumbnails(movies);
    }

}
