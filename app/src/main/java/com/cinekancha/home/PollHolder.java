package com.cinekancha.home;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class PollHolder extends HomeItemHolder {
	
	@BindView(R.id.question)
	public TextView question;
	
	@BindView(R.id.options_container)
	public RadioGroup answerContainer;
	
	public PollHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
	}
}
