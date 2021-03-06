package com.cinekancha.actor;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.ActorPhoto;
import com.cinekancha.entities.model.Photos;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.CharacterItemDecoration;
import com.cinekancha.utils.ScreenUtils;
import com.cinekancha.view.CineActorPhotoViewModel;
import com.stfalcon.frescoimageviewer.ImageViewer;

import butterknife.BindView;

public class ActorDetailActivity extends BaseNavigationActivity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.movieRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.homeSwipeRefreshLayout)
    public SwipeRefreshLayout homeSwipeRefreshLayout;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    private CineActorPhotoViewModel cineActorPhotoViewModel;

    private ActorPhotoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cineActorPhotoViewModel = ViewModelProviders.of(this).get(CineActorPhotoViewModel.class);
        cineActorPhotoViewModel.setActorID(Integer.parseInt(getIntent().getStringExtra("actor")));
        init();
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.photoGallery);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        int spanCount = 2; // 3 columns
        int spacing = ScreenUtils.dpToPx(this, 16); // 50px
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new CharacterItemDecoration(spanCount, spacing, includeEdge));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        homeSwipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cineActorPhotoViewModel.getActorPhoto() == null) {
            requestMovie();
        } else {
            renderMovieData();
        }
    }

    private void renderMovieData() {
        if (cineActorPhotoViewModel.getActorPhoto() != null) {
            adapter = new ActorPhotoAdapter(cineActorPhotoViewModel.getActorPhoto().getPhotos(), this);
            recyclerView.setAdapter(adapter);
        } else requestMovie();
    }

    private void requestMovie() {
            compositeDisposable.add(RestAPI.getInstance().getActorPhoto(cineActorPhotoViewModel.getActorID())
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleMovieFetchError));
    }

    private void handleDatabase(ActorPhoto data) {
        handleMovieData(data);
    }

    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleMovieData(ActorPhoto data) {
        if (data!=null) {
            cineActorPhotoViewModel.setActorPhoto(data);
            renderMovieData();
        }
        else
            Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int id) {
        new ImageViewer.Builder<>(this, cineActorPhotoViewModel.getActorPhoto().getPhotos())
                .setFormatter(Photos::getUrl)
                .setStartPosition(id)
                .show();
    }

    @Override
    public void onRefresh() {
        requestMovie();
    }
}
