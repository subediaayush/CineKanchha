package com.cinekancha.article;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.adapters.base.RecyclerViewClickListener;
import com.cinekancha.view.CineArticleViewModel;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class ArticleListActivity extends BaseNavigationActivity implements RecyclerViewClickListener {
	@BindView(R.id.list_view)
	public RecyclerView mArticleList;
	
	private CineArticleViewModel mCineArticleViewModel;
	private ArticleAdapter mArticleAdapter;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mCineArticleViewModel = ViewModelProviders.of(this).get(CineArticleViewModel.class);
		
		mArticleAdapter = new ArticleAdapter();
		
		mArticleList.setLayoutManager(new LinearLayoutManager(this));
		mArticleList.setAdapter(mArticleAdapter);
		mArticleAdapter.setOnClickListener(this);
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.activity_general_list;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (mCineArticleViewModel.getArticles() == null) {
			requestArticles(null, 50);
		} else {
			mArticleAdapter.setArticles(mCineArticleViewModel.getArticles());
		}
	}
	
	private void requestArticles(String cursor, int count) {
		// Request article here
	}
	
	@Override
	public void onClick(View v, int position) {
		long id = mArticleAdapter.getArticle(position).getId();
		Intent detail = new Intent(this, ArticleDetailActivity.class);
		detail.putExtra("article", id);
		startActivity(detail);
	}
}
