package com.cinekancha.poll;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cinekancha.R;

import butterknife.BindView;

public class PollRadioVH extends PollVH {

    @BindView(R.id.rdPoll)
    public RadioButton rdPoll;

    public PollRadioVH(View itemView) {
        super(itemView);
    }
}
