package com.cinekancha.article;

import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.Article;
import com.cinekancha.home.FeaturedNewsHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class ArticleAdapter extends BaseRecyclerAdapter<FeaturedNewsHolder> {
	private List<Article> mData;
	
	@Override
	public FeaturedNewsHolder onCreateView(int viewType, View view) {
		return new FeaturedNewsHolder(this, view);
	}
	
	@Override
	public int[] getLayoutsForViewType() {
		return new int[] {
				R.layout.layout_featured_articles
		};
	}
	
	@Override
	protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
		FeaturedNewsHolder holder = (FeaturedNewsHolder) baseHolder;
		Article news = mData.get(position);
		holder.title.setText(news.getTitle());
	}
	
	@Override
	public int getItemCount() {
		return mData == null ? 0 : mData.size();
	}
	
	public void setArticles(List<Article> articles) {
		this.mData = articles;
		notifyDataSetChanged();
	}
	
	public void addArticles(@NonNull  List<Article> articles) {
		if (this.mData == null) this.mData = new ArrayList<>();
		int initial = this.mData.size();
		this.mData.addAll(articles);
		notifyItemRangeInserted(initial, articles.size());
	}
	
	public Article getArticle(int position) {
		return mData.get(position % mData.size());
	}
}
