package com.cinekancha.poll;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RadioButton;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.Option;
import com.cinekancha.entities.model.PollData;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.listener.OnPollClickListener;
import com.cinekancha.utils.PollUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class PollAdapter extends BaseRecyclerAdapter<PollsHolder> {
    private List<PollData> pollDataList = new ArrayList<>();
    private OnPollClickListener listener;

    public PollAdapter(List<PollData> pollDataList, OnPollClickListener listener) {
        this.pollDataList = pollDataList;
        this.listener = listener;
    }

    public PollAdapter(OnPollClickListener listener) {
        this.listener = listener;
    }

    public void setPollDataList(List<PollData> pollDataList) {
        this.pollDataList.clear();
        this.pollDataList.addAll(pollDataList);
        notifyDataSetChanged();
    }

    public void addPollDataList(List<PollData> pollDataList) {
        this.pollDataList.addAll(pollDataList);
        notifyDataSetChanged();
    }

    @Override
    public PollsHolder onCreateView(int viewType, View view) {
        return new PollsHolder(this, view);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.adapter_poll
        };
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        PollsHolder holder = (PollsHolder) baseHolder;
        PollData poll = pollDataList.get(position);
        holder.txtQuestion.setText(poll.getQuestion());
        if (poll.getStatus().equalsIgnoreCase("INACTIVE")) {
            pollInactive(holder, poll);
        } else {
            pollActive(holder, poll);
        }
    }

    private void pollInactive(PollsHolder holder, PollData poll) {
        holder.txtPreviousResult.setVisibility(View.VISIBLE);
        holder.submitButton.setVisibility(View.GONE);
        holder.lytBorder.setVisibility(View.VISIBLE);
        holder.answerContainer.setVisibility(View.GONE);
        holder.lytWhole.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        PollItemAdapter adapter = new PollItemAdapter(poll.getOptions(), new OnClickListener() {
            @Override
            public void onClick(int id) {

            }
        }, poll.getStatus(), poll.getTotalVotes());
        holder.answerRecycler.setAdapter(adapter);
    }


    private void pollActive(PollsHolder holder, PollData poll) {
        holder.txtPreviousResult.setVisibility(View.GONE);
        holder.answerRecycler.setVisibility(View.GONE);
        holder.submitButton.setVisibility(View.VISIBLE);
        holder.lytBorder.setVisibility(View.GONE);
        holder.lytWhole.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.backgroundPoll));
        holder.answerContainer.removeAllViews();

        holder.answerContainer.setTag(poll.getId());
        boolean isAnswered = PollUtil.isAnswered(poll.getId());
        int answerIndex = PollUtil.getAnswered(poll.getId());

        List<Option> options = poll.getOptions();

        for (int i = 0; i < options.size(); i++) {
            Option option = options.get(i);
            RadioButton optionButton = new RadioButton((holder.itemView.getContext()));
            optionButton.setId(options.get(i).getId());
            optionButton.setTag(i);
            optionButton.setText(option.getText());
            holder.answerContainer.addView(optionButton);

            if (isAnswered && answerIndex == i)
                holder.answerContainer.check(optionButton.getId());
        }
        if (isAnswered) {
            holder.answerContainer.setEnabled(false);
            holder.submitButton.setEnabled(false);
        } else {
            holder.answerContainer.setEnabled(true);
            holder.submitButton.setEnabled(true);
            holder.submitButton.setOnClickListener(view ->
                    listener.onClick(holder.answerContainer.getCheckedRadioButtonId(), poll.getId()));
        }
    }

    @Override
    public int getItemCount() {
        return pollDataList.size();
    }
}
