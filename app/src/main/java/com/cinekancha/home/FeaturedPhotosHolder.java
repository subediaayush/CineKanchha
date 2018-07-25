package com.cinekancha.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.entities.GalleryItem;
import com.cinekancha.entities.ThumbWrapper;
import com.cinekancha.entities.ThumbnailConverter;

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
	
	public FeaturedPhotosHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
		setIsRecyclable(false);
		adapter = new ThumbnailAdapter<>(R.layout.layout_featured_photo, new ThumbnailConverter<GalleryItem>() {
			@Override
			public ThumbWrapper convert(GalleryItem data) {
				return new ThumbWrapper(
						data.getImageUrl(),
						data.getName()
				);
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
		
		title.setText("Photo Gallery");
	}
	
	public void setMovies(List<GalleryItem> movies) {
		adapter.setThumbnails(movies);
	}
	
}
