package com.cinekancha.view;

import android.app.Application;
import androidx.annotation.NonNull;

import com.cinekancha.entities.model.Article;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineNewsGossipsViewModel extends BasePaginationViewModel {
    private List<Article> newsGossipList;
    private List<Article> appendNewsGossipList;

    public CineNewsGossipsViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void resetState() {
        newsGossipList = null;
        appendNewsGossipList = null;
    }

    public List<Article> getNewsGossipList() {
        return newsGossipList;
    }

    public void setNewsGossipList(List<Article> newsGossipList) {
        if (this.newsGossipList == null) {
            this.newsGossipList = newsGossipList;
        } else this.newsGossipList.addAll(newsGossipList);
    }

    public List<Article> getAppendNewsGossipList() {
        return appendNewsGossipList;
    }

    public void setAppendNewsGossipList(List<Article> appendNewsGossipList) {
        this.appendNewsGossipList = appendNewsGossipList;
    }
}
