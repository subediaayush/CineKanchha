package com.cinekancha.home;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.entities.ThumbnailConverter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class ThumbnailViewHolder<T> extends HomeItemHolder {
	
	@BindView(R.id.list)
	public RecyclerView list;
	@BindView(R.id.label)
	public TextView title;
	
	private ThumbnailAdapter<T> adapter;
	
	public ThumbnailViewHolder(BaseRecyclerAdapter baseRecyclerAdapter,
	                           View view,
	                           String title,
	                           @LayoutRes int thumbnailLayout,
	                           ThumbnailConverter<T> converter) {
		super(baseRecyclerAdapter, view);
		setIsRecyclable(false);
		
		this.title.setText(title);
		
		adapter = new ThumbnailAdapter<>(thumbnailLayout, converter);
		list.setAdapter(adapter);
		
		Context context = view.getContext();
		LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {
			@Override
			public boolean canScrollVertically() {
				return false;
			}
		};
		list.setNestedScrollingEnabled(false);
		list.setLayoutManager(manager);
		
		DividerItemDecoration decoration = new DividerItemDecoration(list.getContext(), DividerItemDecoration.HORIZONTAL);
		decoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider_transparent));
		list.addItemDecoration(decoration);
	}
	
	public void setThumbnails(List<T> thumbnails) {
		adapter.setThumbnails(thumbnails);
	}
}
