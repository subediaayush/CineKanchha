package com.cinekancha.newsGossips;

import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.Article;
import com.cinekancha.movieDetail.RatingHolder;

import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class NewsGossipAdapter extends BaseRecyclerAdapter<NewsGossipHolder> {
    private List<Article> mData;

    @Override
    public NewsGossipHolder onCreateView(int viewType, View view) {
        return new NewsGossipHolder(this, view);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.adapter_news_gossips
        };
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
//		FeaturedNewsHolder holder = (FeaturedNewsHolder) baseHolder;
//		Article news = mData.get(position);
//		holder.title.setText(news.getTitle());
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void setArticles(List<Article> articles) {
        this.mData = articles;
        notifyDataSetChanged();
    }

}
