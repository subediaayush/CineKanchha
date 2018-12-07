package com.cinekancha.movieDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.adapters.base.RecyclerViewClickListener;
import com.cinekancha.article.ArticleDetailActivity;
import com.cinekancha.entities.model.Article;
import com.cinekancha.entities.model.FeaturedContent;
import com.cinekancha.entities.model.Links;
import com.cinekancha.entities.model.MovieDetail;
import com.cinekancha.entities.model.Photo;
import com.cinekancha.entities.model.ReviewData;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.home.OnSlideClickListener;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movieReview.ReviewDetailActivity;
import com.cinekancha.utils.Constants;
import com.cinekancha.utils.GlobalUtils;
import com.cinekancha.view.CinePostMovieViewModel;
import com.cinekancha.view.CircleIndicator;
import com.squareup.picasso.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class MoviePostDetailActivity extends BaseNavigationActivity implements OnSlideClickListener, SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener, OnClickListener {
    @BindView(R.id.imgFeature)
    public ImageView imgFeature;

    @BindView(R.id.viewPagerYoutube)
    protected ViewPager viewPagerYoutube;

    @BindView(R.id.indicator)
    protected CircleIndicator mIndicator;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

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

    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.lytDays)
    public LinearLayout lytDays;

    @BindView(R.id.lytYoutube)
    public LinearLayout lytYoutube;

    @BindView(R.id.recycleViewRating)
    public RecyclerView recyclerViewRating;

    @BindView(R.id.recylerViewPhotos)
    public RecyclerView recylerViewPhotos;

    @BindView(R.id.recylerView)
    public RecyclerView recylerView;

    private CinePostMovieViewModel mCinePostMovieModel;

    private RatingAdapter adapter;
    private PhotoAdapter photoAdapter;
    private MovieArticleAdapter articleAdapter;

    private String videoId = "";
    private SlideaYoutubeAdapter mSlideAdapter;
    String days = "";
    private MovieDetail data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCinePostMovieModel = ViewModelProviders.of(this).get(CinePostMovieViewModel.class);
        mCinePostMovieModel.setMovieID(Integer.parseInt(getIntent().getStringExtra("movie")));
        init();
    }

    private void init() {
        recyclerViewRating.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRating.setNestedScrollingEnabled(false);
        recyclerViewRating.setHasFixedSize(true);

        recylerViewPhotos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recylerViewPhotos.setNestedScrollingEnabled(false);
        recylerViewPhotos.setHasFixedSize(true);


        recylerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recylerView.setNestedScrollingEnabled(false);
        recylerView.setHasFixedSize(true);

        adapter = new RatingAdapter();
        articleAdapter = new MovieArticleAdapter(this);
        photoAdapter = new PhotoAdapter();

        photoAdapter.setOnClickListener(this);

        recylerViewPhotos.setAdapter(photoAdapter);
        recyclerViewRating.setAdapter(adapter);
        recylerView.setAdapter(articleAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);

        mSlideAdapter = new SlideaYoutubeAdapter(getSupportFragmentManager(), viewPagerYoutube);

        viewPagerYoutube.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mSlideAdapter.startSlideshow();
                } else {
                    mSlideAdapter.stopSlideshow();
                }
            }
        });
        mSlideAdapter.setOnSlideClickListener(this);
        viewPagerYoutube.setAdapter(mSlideAdapter);

        mIndicator.setViewPager(viewPagerYoutube);
        mSlideAdapter.registerDataSetObserver(mIndicator.getDataSetObserver());
        btnReview.setOnClickListener(view -> {
            if (data != null && !TextUtils.isEmpty(data.getReview()) && data.getReview() != null) {
                ReviewData reviewData = new ReviewData();
                reviewData.setId(data.getId());
                reviewData.setFeaturedImage(data.getFeaturedImage());
                reviewData.setName(data.getName());
                reviewData.setReview(data.getReview());
                Intent intent = new Intent(MoviePostDetailActivity.this, ReviewDetailActivity.class);
                intent.putExtra("review", (Parcelable) reviewData);
                startActivity(intent);
            }
        });
    }

    private String compareDate(String releaseDateApi) {
        Date currentDate = Calendar.getInstance().getTime();
        String type = "";
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate = null;
        try {
            releaseDate = curFormater.parse(releaseDateApi);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (releaseDate.after(currentDate)) {
            Calendar releaseDay = Calendar.getInstance();
            releaseDay.setTime(releaseDate);
            long diff = releaseDay.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
            days = String.valueOf(diff / (24 * 60 * 60 * 1000));
            type = "pre";
        } else if (releaseDate.before(currentDate))
            type = "post";
        else if (releaseDate.equals(currentDate))
            type = "post";
        return type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_movie_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mCinePostMovieModel.getMovie() == null) {
            requestMovie(mCinePostMovieModel.getMovieId());
        } else {
            try {
                renderMovieData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void renderMovieData() throws MalformedURLException {
        swipeRefreshLayout.setRefreshing(false);
        data = mCinePostMovieModel.getMovie();
        getSupportActionBar().setTitle(data.getName());
        if (data != null) {
            String releaseType = "";
            if (data.getReleaseDate() != null)
                releaseType = compareDate(data.getReleaseDate());
            if (!TextUtils.isEmpty(releaseType) && releaseType.equals("pre")) {
                btnReview.setVisibility(View.GONE);
                lytDays.setVisibility(View.VISIBLE);
                txtDays.setText(days);
            } else if (!TextUtils.isEmpty(releaseType) && releaseType.equals("post")) {
                btnReview.setVisibility(View.VISIBLE);
                lytDays.setVisibility(View.GONE);
            }
            if (data.getLinks().size() > 0 && data.getLinks() != null)
                lytYoutube.setVisibility(View.VISIBLE);
            else
                lytYoutube.setVisibility(View.GONE);
            if (data.getPhoto().size() > 0 && data.getPhoto() != null) {
                recylerViewPhotos.setVisibility(View.VISIBLE);
                photoAdapter.setData(data.getPhoto());
            } else
                recylerViewPhotos.setVisibility(View.GONE);
            if (data.getArticles().size() > 0 && data.getArticles() != null) {
                recylerView.setVisibility(View.VISIBLE);
                articleAdapter.setArticles(data.getArticles());
            } else
                recylerView.setVisibility(View.GONE);
            List<Links> links = new ArrayList<>();
            links.clear();
            for (Links item : data.getLinks()) {
                String imageUrl = GlobalUtils.extractYoutubeUrl(item.getUrl());
                item.setYoutubeImageUrl(imageUrl);
                links.add(item);
            }

            mSlideAdapter.setFeaturedItems(links);
            mSlideAdapter.startSlideshow();
            Picasso.with(this)
                    .load(Constants.imageUrl + data.getFeaturedImage())
                    .placeholder(R.drawable.placeholder_movie)
                    .into(imgFeature);
            String title = (data.getName() == null) ? "" : data.getName();
            String description = (data.getSynopsis() == null) ? "" : data.getSynopsis();
            String casts = (data.getCasts() == null) ? "" : data.getCasts();
            String director = (data.getCrew().getDirectedBy() == null) ? "" : data.getCrew().getDirectedBy();
            String producer = (data.getCrew().getProducedBy() == null) ? "" : data.getCrew().getProducedBy();
            String music = (data.getCrew().getMusic() == null) ? "" : data.getCrew().getMusic();
            txtTitle.setText(title);
            txtDescription.setText(description);
            txtCasts.setText("Cast: " + casts);
            txtDirector.setText("Director: " + director);
            txtProducer.setText("Producer: " + producer);
            txtMusic.setText("Music: " + music);
        }
    }

    private void requestMovie(int id) {
        compositeDisposable.add(RestAPI.getInstance().getMovieDetail(id)
                .doOnSubscribe(disposable -> {
                    swipeRefreshLayout.setRefreshing(true);
                })
                .doFinally(() -> swipeRefreshLayout.setRefreshing(false))
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
        if (data != null) {
            mCinePostMovieModel.setMovie(data);
            renderMovieData();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSlideClicked(FeaturedContent item) {

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
        requestMovie(mCinePostMovieModel.getMovieId());
    }

    @Override
    public void onClick(View v, int position) {
        new ImageViewer.Builder<>(this, mCinePostMovieModel.getMovie().getPhoto())
                .setFormatter(Photo::getUrl)
                .setStartPosition(position).show();
    }

    @Override
    public void onClick(int id) {
        Article article = mCinePostMovieModel.getMovie().getArticles().get(id);
        ArticleDetailActivity.startActivity(this, article);
    }
}
