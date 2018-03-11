package com.cinekancha.home;

import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class HeaderHolder extends HomeItemHolder {
	
	@BindView(R.id.header)
	TextView header;
	
	public HeaderHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
	}
	
	@Override
	public int[] getClickViewIdList() {
		return new int[] {header.getId()};
	}
}
