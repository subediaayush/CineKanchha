package com.cinekancha.poll;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RadioButton;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.Video;
import com.cinekancha.entities.model.Option;
import com.cinekancha.entities.model.PollData;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.trending.TrendingHolder;
import com.cinekancha.utils.Constants;
import com.cinekancha.utils.GlobalUtils;
import com.cinekancha.utils.PollUtil;
import com.cinekancha.utils.ViewIdGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class PollAdapter extends BaseRecyclerAdapter<PollsHolder> {
    private List<PollData> pollDataList;
    private OnClickListener listener;

    public PollAdapter(List<PollData> pollDataList, OnClickListener listener) {
        this.pollDataList = pollDataList;
        this.listener = listener;
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
        if (poll.getStatus().equalsIgnoreCase("INACTIVE")) {
            holder.txtPreviousResult.setVisibility(View.VISIBLE);
            holder.submitButton.setVisibility(View.GONE);
            holder.lytBorder.setVisibility(View.VISIBLE);
            holder.lytWhole.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        } else {
            holder.txtPreviousResult.setVisibility(View.GONE);
            holder.submitButton.setVisibility(View.VISIBLE);
            holder.lytBorder.setVisibility(View.GONE);
            holder.lytWhole.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.backgroundPoll));
        }
        PollItemAdapter adapter = new PollItemAdapter(poll.getOptions(), new OnClickListener() {
            @Override
            public void onClick(int id) {

            }
        }, poll.getStatus(), poll.getTotalVotes());
        holder.answerRecycler.setAdapter(adapter);

        holder.txtQuestion.setText(poll.getQuestion());

    }

    @Override
    public int getItemCount() {
        return pollDataList.size();
    }
}
