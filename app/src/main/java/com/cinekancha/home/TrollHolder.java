package com.cinekancha.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class TrollHolder extends HomeItemHolder {
	
	@BindView(R.id.troll)
	public ImageView troll;
	
	public TrollHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
		view.setOnClickListener(this);
	}
}
