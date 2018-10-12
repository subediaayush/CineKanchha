package com.cinekancha.poll;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cinekancha.R;

import butterknife.BindView;

public class PollRadioVH extends PollVH {

    public RadioGroup answerContainer;

    public PollRadioVH(View itemView) {
        super(itemView);
        answerContainer = itemView.findViewById(R.id.options_container);
    }
}
