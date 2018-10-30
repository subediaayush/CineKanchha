package com.cinekancha.article;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.Article;
import com.cinekancha.utils.Constants;
import com.cinekancha.view.CineArticleViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class ArticleDetailActivity extends BaseNavigationActivity {
    @BindView(R.id.image)
    public ImageView mArticleImage;

    @BindView(R.id.title)
    public TextView mTitle;

    @BindView(R.id.article)
    public WebView mArticle;

    private CineArticleViewModel mCineArticleViewModel;

    public static void startActivity(Context context, Article article) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra("article", (Parcelable) article);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCineArticleViewModel = ViewModelProviders.of(this).get(CineArticleViewModel.class);
        getSupportActionBar().setTitle("News Gossip");

        if (getIntent() != null) {
            mCineArticleViewModel.setArticle((Article) getIntent().getExtras().getParcelable("article"));
        }

        if (mCineArticleViewModel.getArticle() != null) {
            renderArticle(mCineArticleViewModel.getArticle());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void renderArticle(Article article) {
        if (!TextUtils.isEmpty(article.getImage())) Picasso.with(this)
                .load(Constants.imageUrl + article.getImage())
                .into(mArticleImage);
        mTitle.setText(article.getTitle());
        if (article.getContent() != null)
            mArticle.loadData(article.getContent(), "text/html", "utf-8");
        else
            mArticle.loadData(article.getSummary(), "text/html", "utf-8");
    }
}
