package com.cinekancha.newsGossips;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinekancha.R;
import com.cinekancha.entities.model.Article;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.poll.PollProgressVH;
import com.cinekancha.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class NewsGossipAdapter extends RecyclerView.Adapter<NewsGossipVH> {
    private List<Article> mData = new ArrayList<>();
    private OnClickListener listener;
    private static final int HEADER = 2001;
    private static final int ITEM = 2002;

    public NewsGossipAdapter(List<Article> mData, OnClickListener listener) {
        this.mData = mData;
        this.listener = listener;
    }

    public NewsGossipAdapter(OnClickListener listener) {
        this.listener = listener;
    }

    public void setNewsGossipList(List<Article> articleList) {
        this.mData.clear();
        this.mData.addAll(articleList);
        notifyDataSetChanged();
    }

    public void addNewsGossipList(List<Article> articleList) {
        this.mData.addAll(articleList);
        notifyDataSetChanged();
    }

    @Override
    public NewsGossipVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news_gossips, parent, false);
            return new NewsGossipBackgroundHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news_gossip_featured, parent, false);
            return new NewsGossipHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(NewsGossipVH holder, int position) {
        if (position == 0) {
            Picasso.with(holder.itemView.getContext())
                    .load(Constants.imageUrl + mData.get(position).getImage())
                    .into(((NewsGossipHolder) holder).imgNewsGossip);
            ((NewsGossipHolder) holder).txtNewsGossip.setText(mData.get(position).getTitle());
        } else if (position == mData.size() - 1) {
            Picasso.with(holder.itemView.getContext())
                    .load(Constants.imageUrl + mData.get(position).getImage())
                    .into(((NewsGossipHolder) holder).imgNewsGossip);
            ((NewsGossipHolder) holder).txtNewsGossip.setText(mData.get(position).getTitle());
        } else {
            NewsGossipBackgroundHolder newsGossipHolder = (NewsGossipBackgroundHolder) holder;
            Picasso.with(holder.itemView.getContext())
                    .load(Constants.imageUrl + mData.get(position).getImage())
                    .into(newsGossipHolder.imgNewsGossip);
            newsGossipHolder.txtNewsGossip.setText(mData.get(position).getTitle());
        }
        holder.itemView.setOnClickListener(view -> {
            listener.onClick(position);
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position != 0 && position != mData.size() - 1) {
            return ITEM;
        } else {
            return HEADER;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
