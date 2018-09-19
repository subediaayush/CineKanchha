package com.cinekancha.movieDetail;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cinekancha.BuildConfig;
import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.MovieDetail;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.utils.Constants;
import com.cinekancha.view.CinePostMovieViewModel;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;

public class MoviePostDetailActivity extends BaseNavigationActivity {
    @BindView(R.id.imgBanner)
    public ImageView imgBanner;

    @BindView(R.id.imgFeature)
    public ImageView imgFeature;

    @BindView(R.id.txtBannerHead)
    public TextView txtBannerHead;

    @BindView(R.id.txtTitle)
    public TextView txtTitle;

    @BindView(R.id.txtSubTitle)
    public TextView txtSubTitle;

    @BindView(R.id.txtCasts)
    public TextView txtCasts;

    @BindView(R.id.txtDirector)
    public TextView txtDirector;

    @BindView(R.id.txtProducer)
    public TextView txtProducer;

    @BindView(R.id.btnReview)
    public Button btnReview;

    @BindView(R.id.txtDays)
    public TextView txtDays;

    @BindView(R.id.txtMusic)
    public TextView txtMusic;

    @BindView(R.id.txtDescription)
    public TextView txtDescription;

    @BindView(R.id.lytDays)
    public LinearLayout lytDays;

    @BindView(R.id.recycleViewRating)
    public RecyclerView recycleView;

    private CinePostMovieViewModel mCinePostMovieModel;

    private RatingAdapter adapter;

    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCinePostMovieModel = ViewModelProviders.of(this).get(CinePostMovieViewModel.class);
        Log.d("MovieID", getIntent().getStringExtra("movie"));
        mCinePostMovieModel.setMovieID(Integer.parseInt(getIntent().getStringExtra("movie")));
        init();

    }

    private void init() {
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setHasFixedSize(true);
        adapter = new RatingAdapter();
        recycleView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_movie_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mCinePostMovieModel.getMovie() == null) {
            requestArticle(mCinePostMovieModel.getMovieId());
        } else {
            try {
                renderMovieData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void renderMovieData() throws MalformedURLException {
        MovieDetail data = mCinePostMovieModel.getMovie();
        if (data != null) {
            String videoId = extractYoutubeId(data.getLinks().get(0).getUrl());
            String img_url = "http://img.youtube.com/vi/" + videoId + "/0.jpg"; // this is link which will give u thumnail image of that video
            Picasso.with(this)
                    .load(img_url)
                    .placeholder(R.drawable.placeholder_movie)
                    .into(imgBanner);
            Picasso.with(this)
                    .load(Constants.imageUrl + data.getFeaturedImage())
                    .placeholder(R.drawable.placeholder_movie)
                    .into(imgFeature);
            String type = (data.getLinks().get(0).getType() != null) ? "" : data.getLinks().get(0).getType();
            String title = (data.getName() == null) ? "" : data.getName();
            String casts = (data.getCasts() == null) ? "" : data.getCasts();
            String director = (data.getCrew().getDirectedBy() == null) ? "" : data.getCrew().getDirectedBy();
            String producer = (data.getCrew().getProducedBy() == null) ? "" : data.getCrew().getProducedBy();
            String music = (data.getCrew().getMusic() == null) ? "" : data.getCrew().getMusic();
            txtBannerHead.setText(type);
            txtTitle.setText(title);
            txtCasts.setText("Cast: " + casts);
            txtDirector.setText("Director: " + director);
            txtProducer.setText("Producer: " + producer);
            txtMusic.setText("Music: " + music);
        }
    }

    private void requestArticle(int id) {
        compositeDisposable.add(RestAPI.getInstance().getMovie(id)
                .doOnSubscribe(disposable -> {
//                    mSwipeRefreshLayout.setRefreshing(true);
                })
//                .doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(this::handleMovieData, this::handleMovieFetchError));
    }


    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleMovieData(MovieDetail data) throws MalformedURLException {
        mCinePostMovieModel.setMovie(data);
        renderMovieData();
    }

    public String extractYoutubeId(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
    }

}
