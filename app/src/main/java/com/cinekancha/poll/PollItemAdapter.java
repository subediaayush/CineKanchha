package com.cinekancha.poll;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.Option;
import com.cinekancha.entities.model.PollData;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.PollUtil;
import com.cinekancha.utils.ScreenUtils;
import com.cinekancha.utils.ViewIdGenerator;

import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class PollItemAdapter extends RecyclerView.Adapter<PollVH> {
    private static final int HEADER = 2001;
    private static final int ITEM = 2002;
    private List<Option> pollDataList;
    private OnClickListener listener;
    private String status;
    private int totalVote;

    public PollItemAdapter(List<Option> pollDataList, OnClickListener listener, String status, int totalVote) {
        this.pollDataList = pollDataList;
        this.listener = listener;
        this.status = status;
        this.totalVote = totalVote;
    }


    @Override
    public PollVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_progress_poll, parent, false);
            return new PollProgressVH(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_poll_radio, parent, false);
            return new PollRadioVH(view);
        }
    }

    @Override
    public void onBindViewHolder(PollVH holder, int position) {
        Option option = pollDataList.get(position);
        if (getItemViewType(position) == HEADER) {
            if (option.getVote() != 0) {
                int percentage = calculatePercentage(option.getVote(), totalVote);
                int screenWidth = ScreenUtils.getScreenWidthInDp((Activity) holder.itemView.getContext());
                screenWidth = screenWidth * percentage / 100;
                screenWidth = ScreenUtils.dpToPx(holder.itemView.getContext(), screenWidth);
                ((PollProgressVH) holder).txtProgress.setWidth(screenWidth);
                ((PollProgressVH) holder).txtMovieName.setText(option.getText());
                ((PollProgressVH) holder).txtPercentage.setText(String.valueOf(percentage) + "%");

            } else {
                int screenWidth = ScreenUtils.getScreenWidthInDp((Activity) holder.itemView.getContext());
                screenWidth = ScreenUtils.dpToPx(holder.itemView.getContext(), screenWidth);
                ((PollProgressVH) holder).txtProgress.setWidth(screenWidth);
                ((PollProgressVH) holder).txtMovieName.setText(option.getText());
                ((PollProgressVH) holder).txtPercentage.setText("100%");
            }
        } else {
            ((PollRadioVH) holder).rdPoll.setText(option.getText());
        }
    }

    private int calculatePercentage(int vote, int totalVote) {
        int percentage = (vote / totalVote) * 100;
        return percentage;
    }

    @Override
    public int getItemViewType(int position) {
        if (status.equals("INACTIVE")) {
            return HEADER;
        } else {
            return ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return pollDataList.size();
    }
}
