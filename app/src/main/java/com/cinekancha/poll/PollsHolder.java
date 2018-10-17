package com.cinekancha.poll;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.entities.model.Option;
import com.cinekancha.home.HomeItemHolder;
import com.cinekancha.listener.OnClickListener;

import java.util.List;

import butterknife.BindView;

public class PollsHolder extends HomeItemHolder {

    @BindView(R.id.txtQuestion)
    public TextView txtQuestion;

    @BindView(R.id.txtPreviousResult)
    public TextView txtPreviousResult;

    @BindView(R.id.txtViewAll)
    public TextView txtViewAll;

    @BindView(R.id.lytMain)
    public LinearLayout lytMain;

    @BindView(R.id.lytTitle)
    public LinearLayout lytTitle;

    @BindView(R.id.lytBorder)
    public View lytBorder;

    @BindView(R.id.lytWhole)
    public View lytWhole;

    @BindView(R.id.options_container)
    public RadioGroup answerContainer;

    @BindView(R.id.submit)
    public Button submitButton;

    @BindView(R.id.answerRecycler)
    public RecyclerView answerRecycler;

    public PollsHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        setIsRecyclable(false);
        answerRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        answerRecycler.setNestedScrollingEnabled(false);
        answerRecycler.setHasFixedSize(true);

    }

    @Override
    public int[] getClickViewIdList() {
        return new int[]{
                R.id.submit
        };
    }

}
