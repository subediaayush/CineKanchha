package com.cinekancha.home;

import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.utils.GradientGenartor;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class TriviaHolder extends HomeItemHolder {

//	@BindView(R.id.title)
//	public TextView title;

    @BindView(R.id.trivia)
    public TextView trivia;
    @BindView(R.id.viewMore)
    public TextView viewMore;

    public int randomColorNum;

    public TriviaHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        randomColorNum = GradientGenartor.getRandom();
    }
}
