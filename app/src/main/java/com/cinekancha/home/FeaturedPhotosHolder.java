package com.cinekancha.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.actor.ActorDetailActivity;
import com.cinekancha.actor.ActorListActivity;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.entities.ThumbWrapper;
import com.cinekancha.entities.ThumbnailConverter;
import com.cinekancha.entities.model.GalleryItem;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.EqualSpacingItemDecoration;
import com.cinekancha.utils.GlobalUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class FeaturedPhotosHolder extends HomeItemHolder {

    private final ThumbnailAdapter<GalleryItem> adapter;
    @BindView(R.id.list)
    public RecyclerView upcomingMoviesList;
    @BindView(R.id.label)
    public TextView title;
    @BindView(R.id.txtViewAll)
    public TextView txtViewAll;

    private List<GalleryItem> photo;

    public FeaturedPhotosHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        setIsRecyclable(false);
        txtViewAll.setVisibility(View.VISIBLE);
        txtViewAll.setOnClickListener(view1 -> {
            GlobalUtils.navigateActivity(view.getContext(), false, ActorListActivity.class);
        });
        adapter = new ThumbnailAdapter<>(R.layout.layout_featured_photo, new ThumbnailConverter<GalleryItem>() {
            @Override
            public ThumbWrapper convert(GalleryItem data) {
                return new ThumbWrapper(
                        data.getImageUrl(),
                        data.getName(),
                        (int) (long) data.getId()
                );
            }
        }, new OnClickListener() {
            @Override
            public void onClick(int position) {
                GalleryItem actor = photo.get(position);
                Intent detail = new Intent(view.getContext(), ActorDetailActivity.class);
                detail.putExtra("actor", String.valueOf(actor.getId()));
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
        title.setText("Photo Gallery");
    }

    public void setMovies(List<GalleryItem> movies) {
        photo = movies;
        adapter.setThumbnails(movies);
    }

}
