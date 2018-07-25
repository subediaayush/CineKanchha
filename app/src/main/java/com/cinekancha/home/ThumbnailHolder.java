package com.cinekancha.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.adapters.base.RecyclerViewClickListener;

import butterknife.BindView;
import io.reactivex.annotations.Nullable;

/**
 * Created by aayushsubedi on 3/15/18.
 */

public class ThumbnailHolder extends BaseViewHolder {
	
	@Nullable
	@BindView(R.id.thumbnail)
	public ImageView thumbnail;
	@Nullable
	@BindView(R.id.title)
	public TextView title;
	@Nullable
	@BindView(R.id.subtitle)
	public TextView subTitle;
	
	public ThumbnailHolder(RecyclerViewClickListener listener, View itemView) {
		super(listener, itemView);
	}
	
	@Override
	public int[] getClickViewIdList() {
		return new int[0];
	}
}
