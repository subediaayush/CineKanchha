package com.cinekancha.poll;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.cinekancha.R;
import com.cinekancha.entities.model.Option;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.ScreenUtils;

import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class PollItemAdapter extends RecyclerView.Adapter<PollVH> {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_progress_poll, parent, false);
        return new PollProgressVH(view);
    }

    @Override
    public void onBindViewHolder(PollVH holder, int position) {
        Option option = pollDataList.get(position);
        int percentage = (int) calculatePercentage(option.getVote(), totalVote);
        ViewTreeObserver viewTreeObserver = ((PollProgressVH) holder).rlyt.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    ((PollProgressVH) holder).rlyt.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    ((PollProgressVH) holder).rlyt.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int screenWidth = ((PollProgressVH) holder).rlyt.getMeasuredWidth() - ScreenUtils.dpToPx(((PollProgressVH) holder).itemView.getContext(), 43);
                screenWidth = screenWidth * percentage / 100;
                ((PollProgressVH) holder).txtMovieName.setText(option.getText());
                ((PollProgressVH) holder).txtProgress.setWidth(screenWidth);
                ((PollProgressVH) holder).txtPercentage.setText(String.valueOf(percentage) + "%");
            }
        });
    }


    private double calculatePercentage(int vote, int totalVote) {
        if (vote == 0) return 0;
        return ((double) vote / totalVote) * 100;
    }

    @Override
    public int getItemCount() {
        return pollDataList.size();
    }
}
