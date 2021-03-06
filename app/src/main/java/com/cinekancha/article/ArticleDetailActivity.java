package com.cinekancha.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cinekancha.BuildConfig;
import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.Article;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.utils.Constants;
import com.cinekancha.view.CineArticleViewModel;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import io.reactivex.Observable;

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

    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    private CineArticleViewModel mCineArticleViewModel;

    public static void startActivity(Context context, Article article) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra("article", (Parcelable) article);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSwipeRefreshLayout.setEnabled(false);

        mCineArticleViewModel = ViewModelProviders.of(this).get(CineArticleViewModel.class);
        getSupportActionBar().setTitle(R.string.app_name);

        if (getIntent() != null) {
            mCineArticleViewModel.setArticle(getIntent().getExtras().getParcelable("article"));
        }

        if (mCineArticleViewModel.getArticle() != null) {
            renderArticle(mCineArticleViewModel.getArticle());
        } else {
            requestArticle();
        }
    }

    private void requestArticle() {
        compositeDisposable.add(getApi()
                .doOnSubscribe(disposable -> {
                    mSwipeRefreshLayout.setRefreshing(true);
                })
                .doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(this::loadAndRenderArticle, this::finish));
    }
    
    private Observable<Article> getApi() {
        if (getIntent().hasExtra("articleId"))
            return RestAPI.getInstance().getArticle(getContentId());
        else
            return RestAPI.getInstance().getNews(getContentId());
    }
    
    private void loadAndRenderArticle(Article article) {
        mCineArticleViewModel.setArticle(article);
        renderArticle(mCineArticleViewModel.getArticle());
    }

    private void finish(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(this, "error " + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unknown error occured", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private long getContentId() {
        if (getIntent().hasExtra("articleId"))
            return Long.parseLong(getIntent().getStringExtra("articleId"));
        else
            return Long.parseLong(getIntent().getStringExtra("newsId"));
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
        mArticle.getSettings().setJavaScriptEnabled(true);

        if (article.getContent() != null)
            mArticle.loadData(article.getContent(), "text/html", "utf-8");
        else
            mArticle.loadData(article.getSummary(), "text/html", "utf-8");
    }
}
