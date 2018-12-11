package com.cinekancha.view;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import com.cinekancha.entities.model.Article;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineArticleViewModel extends AndroidViewModel {
	
	private Article mArticle;
	private int mArticleId;
	
	private List<Article> mArticles;
	
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
	
	public List<Article> getArticles() {
		return mArticles;
	}
	
	public void setArticles(List<Article> articles) {
		this.mArticles = articles;
	}
}
