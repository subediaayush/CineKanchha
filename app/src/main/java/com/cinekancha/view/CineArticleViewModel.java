package com.cinekancha.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.Article;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineArticleViewModel extends AndroidViewModel {
	
	private Article mArticle;
	private int mArticleId;
	
	public CineArticleViewModel(@NonNull Application application) {
		super(application);
	}
	
	public Article getArticle() {
		return mArticle;
	}
	
	public void setArticle(Article article) {
		this.mArticle = article;
	}
	
	public void setArticleId(int articleId) {
		mArticleId = articleId;
	}
	
	public int getArticleId() {
		return mArticleId;
	}
}
