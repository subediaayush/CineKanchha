package com.cinekancha.home;

import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.Movie;

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
	
	}
	
	@Override
	public int getItemCount() {
		return mData.getItemCount();
	}
	
	
	public void setHomeData(HomeData data) {
		this.mData = HomeDataWrapper.wrap(data);
		notifyDataSetChanged();
	}
	
	public void addMovies(List<Movie> movies) {
		if (this.mData != null) {
			int start = this.mData.addMovies(movies);
			notifyItemRangeInserted(start, movies.size());
		}
	}
}
