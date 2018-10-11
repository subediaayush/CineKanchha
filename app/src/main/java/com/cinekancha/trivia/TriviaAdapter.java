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

import io.reactivex.annotations.NonNull;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class TriviaAdapter extends BaseRecyclerAdapter<TriviaHolder> {
    private List<TriviaData> mData;

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
        int random = GradientGenartor.getRandom();
        Drawable drawable = GradientGenartor.generate(random);
        holder.itemView.setBackground(drawable);
        holder.trivia.setText(triviaData.getTrivia());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setTrivias(List<TriviaData> triviaData) {
        this.mData = triviaData;
        notifyDataSetChanged();
    }

    public void addTrivias(@NonNull List<TriviaData> triviaData) {
        if (this.mData == null) this.mData = new ArrayList<>();
        int initial = this.mData.size();
        this.mData.addAll(triviaData);
        notifyItemRangeInserted(initial, triviaData.size());
    }

    public TriviaData getTrivia(int position) {
        return mData.get(position % mData.size());
    }
}
