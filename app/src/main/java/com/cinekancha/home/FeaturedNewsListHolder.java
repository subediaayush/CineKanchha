package com.cinekancha.home;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.article.ArticleDetailActivity;
import com.cinekancha.entities.ThumbWrapper;
import com.cinekancha.entities.ThumbnailConverter;
import com.cinekancha.entities.model.Article;
import com.cinekancha.newsGossips.NewsGossipsActivity;
import com.cinekancha.utils.EqualSpacingItemDecoration;
import com.cinekancha.utils.GlobalUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class FeaturedNewsListHolder extends HomeItemHolder {

    private final ThumbnailAdapter<Article> adapter;
    @BindView(R.id.list)
    public RecyclerView upcomingMoviesList;
    @BindView(R.id.label)
    public TextView title;
    @BindView(R.id.txtViewAll)
    public TextView txtViewAll;

    public List<Article> articles;

    public FeaturedNewsListHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
        super(baseRecyclerAdapter, view);
        setIsRecyclable(false);
        txtViewAll.setVisibility(View.VISIBLE);
        txtViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalUtils.navigateActivity(view.getContext(), false, NewsGossipsActivity.class);
            }
        });
        adapter = new ThumbnailAdapter<>(R.layout.layout_featured_news, new ThumbnailConverter<Article>() {
            @Override
            public ThumbWrapper convert(Article data) {
                return new ThumbWrapper(
                        data.getImage(),
                        data.getTitle(),
                        data.getId()
                );
            }
        }, id -> {
            Article article = articles.get(id);
            ArticleDetailActivity.startActivity(view.getContext(), article);
        });
        upcomingMoviesList.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        Context context = view.getContext();
        upcomingMoviesList.setNestedScrollingEnabled(false);
        upcomingMoviesList.setLayoutManager(manager);
        upcomingMoviesList.addItemDecoration(new EqualSpacingItemDecoration(16, EqualSpacingItemDecoration.VERTICAL));
    }

    public void setNews(List<Article> movies) {
        articles = movies;
        adapter.setThumbnails(movies);
    }

}
