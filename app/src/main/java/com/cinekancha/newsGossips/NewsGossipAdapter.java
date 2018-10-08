package com.cinekancha.newsGossips;

import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.Article;
import com.cinekancha.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class NewsGossipAdapter extends BaseRecyclerAdapter<NewsGossipHolder> {
    private List<Article> mData = new ArrayList<>();

    @Override
    public NewsGossipHolder onCreateView(int viewType, View view) {
        return new NewsGossipHolder(this, view);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.adapter_news_gossip_featured,
                R.layout.adapter_news_gossips
        };
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        NewsGossipHolder newsGossipHolder = (NewsGossipHolder) baseHolder;
        Picasso.with(newsGossipHolder.imgNewsGossip.getContext())
                .load(Constants.imageUrl + mData.get(position).getImage())
                .into(newsGossipHolder.imgNewsGossip);
        newsGossipHolder.txtNewsGossip.setText(mData.get(position).getTitle());
    }

    @Override
    protected void setViewOfTypeOne(BaseViewHolder baseHolder, int position) {
        NewsGossipHolder newsGossipHolder = (NewsGossipHolder) baseHolder;
        Picasso.with(newsGossipHolder.imgNewsGossip.getContext())
                .load(Constants.imageUrl + mData.get(position).getImage())
                .into(newsGossipHolder.imgNewsGossip);
        newsGossipHolder.txtNewsGossip.setText(mData.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setArticles(List<Article> articles) {
        this.mData = articles;
        notifyDataSetChanged();
    }
}
