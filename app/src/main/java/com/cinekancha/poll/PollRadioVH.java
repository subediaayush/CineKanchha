package com.cinekancha.poll;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cinekancha.R;

import butterknife.BindView;

public class PollRadioVH extends PollVH {

    public RadioButton rdPoll;

    public PollRadioVH(View itemView) {
        super(itemView);
        rdPoll = itemView.findViewById(R.id.rdPoll);
    }
}
