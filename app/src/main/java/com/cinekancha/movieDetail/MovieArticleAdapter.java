package com.cinekancha.movieDetail;

import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.Article;
import com.cinekancha.home.FeaturedNewsHolder;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class MovieArticleAdapter extends BaseRecyclerAdapter<ArticleHolder> {
    private List<Article> mData;
    private OnClickListener listener;

    public MovieArticleAdapter(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ArticleHolder onCreateView(int viewType, View view) {
        return new ArticleHolder(this, view);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.adapter_article
        };
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        ArticleHolder holder = (ArticleHolder) baseHolder;
        Article article = mData.get(position);
        holder.txtMovieName.setText(article.getTitle());
        holder.txtSummary.setText(article.getSummary());
        holder.itemView.setOnClickListener(view -> listener.onClick(position));
        Picasso.with(holder.itemView.getContext()).load(Constants.imageUrl + article.getImage()).placeholder(R.drawable.placeholder_movie).into(holder.imgArticle);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setArticles(List<Article> articles) {
        this.mData = articles;
        notifyDataSetChanged();
    }

    public void addArticles(@NonNull List<Article> articles) {
        if (this.mData == null) this.mData = new ArrayList<>();
        int initial = this.mData.size();
        this.mData.addAll(articles);
        notifyItemRangeInserted(initial, articles.size());
    }

    public Article getArticle(int position) {
        return mData.get(position % mData.size());
    }
}
