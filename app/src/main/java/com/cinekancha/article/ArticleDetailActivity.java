package com.cinekancha.article;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.Article;
import com.cinekancha.view.CineArticleViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class ArticleDetailActivity extends BaseNavigationActivity {
	@BindView(R.id.image)
	public ImageView mArticleImage;
	
	@BindView(R.id.title)
	public TextView mTitle;
	
	@BindView(R.id.article)
	public WebView mArticle;
	
	private CineArticleViewModel mCineArticleViewModel;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mCineArticleViewModel = ViewModelProviders.of(this).get(CineArticleViewModel.class);
		
		mCineArticleViewModel.setArticleId(getIntent().getIntExtra("article", -1));
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.activity_article_detail;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (mCineArticleViewModel.getArticle() == null) {
			requestArticle(mCineArticleViewModel.getArticleId());
		} else {
			renderArticle(mCineArticleViewModel.getArticle());
		}
	}
	
	private void renderArticle(Article article) {
		if (!TextUtils.isEmpty(article.getImage())) Picasso.with(this)
				.load(article.getImage())
				.into(mArticleImage);
		mTitle.setText(article.getTitle());
		mArticle.loadData(article.getContent(), "text/html", "utf-8");
	}
	
	private void requestArticle(int id) {
		// Request article here
	}
}
