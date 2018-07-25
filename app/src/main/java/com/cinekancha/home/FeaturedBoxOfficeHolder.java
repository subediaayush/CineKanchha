package com.cinekancha.home;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.BoxOfficeAdapter;
import com.cinekancha.entities.model.BoxOfficeItem;

import java.util.List;

import butterknife.BindView;

class FeaturedBoxOfficeHolder extends HomeItemHolder {
	
	@BindView(R.id.movies)
	public RecyclerView boxOffice;
	
	private BoxOfficeAdapter adapter;
	
	public FeaturedBoxOfficeHolder(HomeDataAdapter homeDataAdapter, View view) {
		super(homeDataAdapter, view);
		view.setOnClickListener(this);
		
		boxOffice.setLayoutManager(new LinearLayoutManager(view.getContext()) {
			@Override
			public boolean canScrollHorizontally() {
				return false;
			}
			
			@Override
			public boolean canScrollVertically() {
				return false;
			}
		});
		
		adapter = new BoxOfficeAdapter();
		Drawable divider = ContextCompat.getDrawable(view.getContext(), R.drawable.list_divider);
		DividerItemDecoration decor = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
		decor.setDrawable(divider);
		boxOffice.addItemDecoration(decor);
		boxOffice.setAdapter(adapter);
	}
	
	public void setBoxOffice(List<BoxOfficeItem> items) {
		adapter.setBoxOfficeItems(items);
	}
}
