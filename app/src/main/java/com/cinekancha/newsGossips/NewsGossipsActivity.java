package com.cinekancha.newsGossips;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.FeaturedItem;
import com.cinekancha.entities.model.Links;
import com.cinekancha.entities.model.MovieDetail;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.home.OnSlideClickListener;
import com.cinekancha.movieDetail.RatingAdapter;
import com.cinekancha.movieDetail.SlideaYoutubeAdapter;
import com.cinekancha.utils.Constants;
import com.cinekancha.utils.GlobalUtils;
import com.cinekancha.view.CineNewsGossipsViewModel;
import com.cinekancha.view.CinePostMovieViewModel;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

public class NewsGossipsActivity extends BaseNavigationActivity implements OnSlideClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.imgNews)
    public ImageView imgNews;

    @BindView(R.id.txtNews)
    public TextView txtNews;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.newsSwipeToRefresh)
    public SwipeRefreshLayout newsSwipeToRefresh;

    @BindView(R.id.recyclerViewNews)
    public RecyclerView recyclerViewNews;

    private CineNewsGossipsViewModel mCineNewsGossipsModel;

    private RatingAdapter adapter;

    private String videoId = "";
    private int movieId;
    String days = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCineNewsGossipsModel = ViewModelProviders.of(this).get(CineNewsGossipsViewModel.class);
        mCineNewsGossipsModel.setMovieID(Integer.parseInt(getIntent().getStringExtra("movie")));
        toolbar.setTitle(getString(R.string.app_name));
        init();
    }

    private void init() {
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewNews.setNestedScrollingEnabled(false);
        recyclerViewNews.setHasFixedSize(true);
//        adapter = new RatingAdapter();
        recyclerViewNews.setAdapter(adapter);
        newsSwipeToRefresh.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_movie_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mCineNewsGossipsModel.getMovie() == null) {
            requestMovie(mCineNewsGossipsModel.getMovieId());
        } else {
            try {
                renderMovieData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void renderMovieData() throws MalformedURLException {
        newsSwipeToRefresh.setRefreshing(false);
    }

    private void requestMovie(int id) {
        compositeDisposable.add(RestAPI.getInstance().getMovie(id)
                .doOnSubscribe(disposable -> {
//                    mSwipeRefreshLayout.setRefreshing(true);
                })
//                .doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(this::handleMovieData, this::handleMovieFetchError));
    }

    private void startYoutube(String url) throws MalformedURLException {
        videoId = GlobalUtils.extractYoutubeId(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoId));
        startActivity(intent);
    }


    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleMovieData(MovieDetail data) throws MalformedURLException {
        mCineNewsGossipsModel.setMovie(data);
        renderMovieData();
    }


    @Override
    public void onSlideClicked(FeaturedItem item) {

    }

    @Override
    public void onSlideClicked(Links item) {
        try {
            startYoutube(item.getUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        requestMovie(movieId);
    }
}
