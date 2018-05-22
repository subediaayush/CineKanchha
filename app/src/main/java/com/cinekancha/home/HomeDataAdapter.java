package com.cinekancha.home;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.Article;
import com.cinekancha.entities.model.Poll;
import com.cinekancha.entities.model.Trivia;
import com.cinekancha.entities.model.Troll;
import com.cinekancha.utils.PollUtil;
import com.cinekancha.utils.ViewIdGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.cinekancha.home.HomeDataWrapper.FEATURED_ARTICLE;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_ARTICLE_HIGHLIGHTED;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_MOVIE;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_POLL;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_TRIVIA;
import static com.cinekancha.home.HomeDataWrapper.FEATURED_TROLL;
import static com.cinekancha.home.HomeDataWrapper.HEADERS;
import static com.cinekancha.home.HomeDataWrapper.NEW_MOVIES;
import static com.cinekancha.home.HomeDataWrapper.UPCOMING_MOVIES;
/**
 * Created by aayushsubedi on 3/8/18.
 */

public class HomeDataAdapter extends BaseRecyclerAdapter<HomeItemHolder> {
	private static final String TAG = "HomeDataAdapter";
	private HomeDataWrapper mData;
	
	@Override
	public HomeItemHolder onCreateView(int viewType, View view) {
		switch (viewType) {
			case NEW_MOVIES: {
				return new NewReleaseHolder(this, view);
			}
			case UPCOMING_MOVIES: {
				return new UpcomingReleaseHolder(this, view);
			}
			case FEATURED_ARTICLE_HIGHLIGHTED: {
				return new FeaturedNewsHolder(this, view);
			}
			case FEATURED_ARTICLE: {
				return new FeaturedNewsListHolder(this, view);
			}
			case FEATURED_POLL: {
				return new PollHolder(this, view);
			}
			case FEATURED_TRIVIA: {
				return new TriviaHolder(this, view);
			}
			case FEATURED_TROLL: {
				return new TrollHolder(this, view);
			}
			case FEATURED_MOVIE:
			default: {
				return new FeaturedMoviesHolder(this, view);
			}
			case HEADERS: {
				return new HeaderHolder(this, view);
			}
		}
	}
	
	@Override
	public int getItemViewType(int position) {
		return mData.getType(position);
	}
	
	@Override
	public int[] getLayoutsForViewType() {
		return new int[]{
				R.layout.layout_home_header,
				R.layout.layout_featured_movies,
				R.layout.layout_featured_movies,
				R.layout.layout_article_top,
				R.layout.layout_featured_articles,
				R.layout.layout_featured_poll,
				R.layout.layout_featured_trivia,
				R.layout.layout_featured_trolls,
				R.layout.layout_featued_movie,
		};
	}
	
	@Override
	protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
		String header = mData.getItem(position);
		HeaderHolder holder = (HeaderHolder) baseHolder;
		holder.header.setText(header);
	}
	
	@Override
	protected void setViewOfTypeOne(BaseViewHolder baseHolder, int position) {
		NewReleaseHolder holder = (NewReleaseHolder) baseHolder;
		holder.setMovies(mData.getItem(position));
	}
	
	@Override
	protected void setViewOfTypeTwo(BaseViewHolder baseHolder, int position) {
		UpcomingReleaseHolder holder = (UpcomingReleaseHolder) baseHolder;
		holder.setMovies(mData.getItem(position));
	}
	
	@Override
	protected void setViewOfTypeThree(BaseViewHolder baseHolder, int position) {
		FeaturedNewsHolder holder = (FeaturedNewsHolder) baseHolder;
		Article news = mData.getItem(position);
		if (TextUtils.isEmpty(news.getImage())) {
			holder.image.setImageResource(R.drawable.placeholder_movie);
		} else {
			Picasso.with(holder.itemView.getContext())
					.load(news.getImage())
					.into(holder.image);
		}
		
		holder.title.setText(news.getTitle());
		holder.summary.setText(news.getSummary());
	}
	
	@Override
	protected void setViewOfTypeFour(BaseViewHolder baseHolder, int position) {
		FeaturedNewsListHolder holder = (FeaturedNewsListHolder) baseHolder;
		Article news = mData.getItem(position);
		if (TextUtils.isEmpty(news.getImage())) {
			holder.image.setImageResource(R.drawable.placeholder_movie);
		} else {
			Picasso.with(holder.itemView.getContext())
					.load(news.getImage())
					.into(holder.image);
		}
		holder.title.setText(news.getTitle());
		holder.summary.setText(news.getSummary());
	}
	
	@Override
	protected void setViewOfTypeFive(BaseViewHolder baseHolder, int position) {
		PollHolder holder = (PollHolder) baseHolder;
		Poll poll = mData.getItem(position);
		
		holder.question.setText(poll.getQuestion());
		holder.answerContainer.removeAllViews();
		
		holder.answerContainer.setTag(poll.getId());
		
		boolean isAnswered = PollUtil.isAnswered(poll.getId());
		int answerIndex = PollUtil.getAnswered(poll.getId());
		
		List<String> options = poll.getOptions();
		
		for (int i = 0; i < options.size(); i++) {
			String option = options.get(i);
			RadioButton optionButton = new RadioButton((baseHolder.itemView.getContext()));
			optionButton.setId(ViewIdGenerator.generateViewId());
			optionButton.setTag(i);
			optionButton.setText(option);
			holder.answerContainer.addView(optionButton);
			
			if (isAnswered && answerIndex == i) holder.answerContainer.check(optionButton.getId());
		}
		
		if (isAnswered) {
			holder.answerContainer.setEnabled(false);
			holder.submitButton.setEnabled(false);
		} else {
			holder.answerContainer.setEnabled(true);
			holder.submitButton.setEnabled(true);
		}
	}
	
	@Override
	protected void setViewOfTypeSix(BaseViewHolder baseHolder, int position) {
		TriviaHolder holder = (TriviaHolder) baseHolder;
		Trivia trivia = mData.getItem(position);
		
		holder.title.setText("Did you know?");
		holder.trivia.setText(trivia.getTrivia());
	}
	
	@Override
	protected void setViewOfTypeSeven(BaseViewHolder baseHolder, int position) {
		TrollHolder holder = (TrollHolder) baseHolder;
		Troll troll = mData.getItem(position);
		
		if (!TextUtils.isEmpty(troll.getImage())) {
			Picasso.with(baseHolder.itemView.getContext())
					.load(troll.getImage())
					.into(holder.troll);
		}
	}
	
	@Override
	protected void setViewOfTypeEight(BaseViewHolder baseHolder, int position) {
		FeaturedMoviesHolder holder = (FeaturedMoviesHolder) baseHolder;
		Movie movie = mData.getItem(position);
		if (!TextUtils.isEmpty(movie.getFeaturedImage())) {
			Picasso.with(baseHolder.itemView.getContext())
					.load(movie.getFeaturedImage())
					.into(holder.image);
		} else {
			holder.image.setImageResource(R.drawable.placeholder_movie);
		}
	}
	
	@Override
	public int getItemCount() {
		return mData == null ? 0 : mData.getItemCount();
	}
	
	
	public void setHomeData(HomeData data) {
		this.mData = HomeDataWrapper.wrap(data);
		Log.i(TAG, "Total items in home: " + mData.getItemCount());
		notifyDataSetChanged();
	}
	
	public void addMovies(List<Movie> movies) {
		if (this.mData != null) {
			int start = this.mData.addMovies(movies);
			notifyItemRangeInserted(start, movies.size());
		}
	}
}
