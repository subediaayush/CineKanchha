package com.cinekancha.poll;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cinekancha.R;

import butterknife.BindView;

public class PollProgressVH extends PollVH {
    //@BindView(R.id.txtMovieName)
    public TextView txtMovieName;
   // @BindView(R.id.txtPercentage)
    public TextView txtPercentage;
    //@BindView(R.id.pbPoll)
    public TextView txtProgress;
    public RelativeLayout rlyt;

    public PollProgressVH(View itemView) {
        super(itemView);
        txtMovieName = itemView.findViewById(R.id.txtMovieName);
        txtPercentage = itemView.findViewById(R.id.txtPercentage);
        txtProgress = itemView.findViewById(R.id.txtProgress);
        rlyt = itemView.findViewById(R.id.rlyt);
    }
}
