package com.cinekancha.home;

import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.utils.PollUtil;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class PollHolder extends HomeItemHolder {

    @BindView(R.id.question)
    public TextView question;

    @BindView(R.id.txtViewAll)
    public TextView txtViewAll;

    @BindView(R.id.options_container)
    public RadioGroup answerContainer;

    @BindView(R.id.submit)
    public Button submitButton;

    public PollHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);

        answerContainer.setOnCheckedChangeListener(
                (group, checkedId) -> PollUtil.setAnswered(
                        (int) group.getTag(),
                        (int) group.findViewById(checkedId).getTag())
        );
    }

    @Override
    public int[] getClickViewIdList() {
        return new int[]{
                R.id.submit
        };
    }
}
