package com.cinekancha.trivia;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.TriviaData;
import com.cinekancha.home.TriviaHolder;
import com.cinekancha.utils.GradientGenartor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class TriviaAdapter extends BaseRecyclerAdapter<TriviaHolder> {
    private List<TriviaData> mData = new ArrayList<>();

    @Override
    public TriviaHolder onCreateView(int viewType, View view) {
        return new TriviaHolder(this, view);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.layout_featured_trivia
        };
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        TriviaHolder holder = (TriviaHolder) baseHolder;
        TriviaData triviaData = mData.get(position);
        Drawable drawable = GradientGenartor.generate(holder.randomColorNum);
        holder.itemView.setBackground(drawable);
        holder.trivia.setText(triviaData.getTrivia());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setTriviaDataList(List<TriviaData> triviaDataList) {
        this.mData.clear();
        this.mData.addAll(triviaDataList);
        notifyDataSetChanged();
    }

    public void addTriviaDataList(List<TriviaData> triviaDataList) {
        this.mData.addAll(triviaDataList);
        notifyDataSetChanged();
    }
}
