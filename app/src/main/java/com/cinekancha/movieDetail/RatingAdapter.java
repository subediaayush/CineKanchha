package com.cinekancha.movieDetail;

import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.Article;
import com.cinekancha.home.FeaturedNewsHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class RatingAdapter extends BaseRecyclerAdapter<RatingHolder> {
    private List<Article> mData;

    @Override
    public RatingHolder onCreateView(int viewType, View view) {
        return new RatingHolder(this, view);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.adapter_rating
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
