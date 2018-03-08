package com.cinekancha.adapters.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by aayushsubedi on 3/8/18.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
	
	private BaseRecyclerAdapter baseRecyclerAdapter;
	
	public BaseViewHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(view);
        this.baseRecyclerAdapter = baseRecyclerAdapter;
        ButterKnife.bind(this, view);
        for (int clickViewId : getClickViewIdList()) {
            view.findViewById(clickViewId).setOnClickListener(this);
        }
    }
	
	@Override
    public void onClick(View view) {
        baseRecyclerAdapter.onClick(view, getLayoutPosition());
    }
	
	public abstract int[] getClickViewIdList();
}
