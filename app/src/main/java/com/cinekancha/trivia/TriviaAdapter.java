package com.cinekancha.trivia;

import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.Trivia;
import com.cinekancha.home.TriviaHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class TriviaAdapter extends BaseRecyclerAdapter<TriviaHolder> {
	private List<Trivia> mData;
	
	@Override
	public TriviaHolder onCreateView(int viewType, View view) {
		return new TriviaHolder(this, view);
	}
	
	@Override
	public int[] getLayoutsForViewType() {
		return new int[] {
				R.layout.layout_featured_trivia
		};
	}
	
	@Override
	protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
		TriviaHolder holder = (TriviaHolder) baseHolder;
		Trivia trivia = mData.get(position);
		
		holder.title.setText("Did you know?");
		holder.trivia.setText(trivia.getTrivia());
	}
	
	@Override
	public int getItemCount() {
		return mData == null ? 0 : mData.size();
	}
	
	public void setTrivias(List<Trivia> trivias) {
		this.mData = trivias;
		notifyDataSetChanged();
	}
	
	public void addTrivias(@NonNull  List<Trivia> trivias) {
		if (this.mData == null) this.mData = new ArrayList<>();
		int initial = this.mData.size();
		this.mData.addAll(trivias);
		notifyItemRangeInserted(initial, trivias.size());
	}
	
	public Trivia getTrivia(int position) {
		return mData.get(position % mData.size());
	}
}
