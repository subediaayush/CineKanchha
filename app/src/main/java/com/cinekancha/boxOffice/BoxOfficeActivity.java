package com.cinekancha.boxOffice;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.BoxOffice;
import com.cinekancha.entities.model.BoxOfficeItem;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.movies.MoviesAdapter;
import com.cinekancha.view.CineBoxOfficeViewModel;

import java.net.MalformedURLException;

import butterknife.BindView;

public class BoxOfficeActivity extends BaseNavigationActivity {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.movieRecyclerView)
    public RecyclerView recyclerView;

    private CineBoxOfficeViewModel cineBoxOfficeViewModel;

    private MoviesAdapter adapter;

    private BoxOffice boxOffice;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (boxOffice.getData() == null) {
            requestBoxOffice();
        } else {
            try {
                renderBoxOfficeData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void renderBoxOfficeData() throws MalformedURLException {
        this.boxOffice = cineBoxOfficeViewModel.getBoxOffice();
        if (boxOffice.getData() != null && boxOffice.getData().size() > 0) {
            for (BoxOfficeItem boxOfficeItem : boxOffice.getData()) {
                requestMovieData(boxOfficeItem);
            }
        } else requestBoxOffice();
    }

    private void requestBoxOffice() {
        compositeDisposable.add(RestAPI.getInstance().getBOxOffice()
                .doOnSubscribe(disposable -> {
//                    mSwipeRefreshLayout.setRefreshing(true);
                })
//                .doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(this::handleBoxOfficeData, this::handleMovieFetchError));
    }

    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleBoxOfficeData(BoxOffice boxOffice) throws MalformedURLException {
        cineBoxOfficeViewModel.setBoxOffice(boxOffice);
        renderBoxOfficeData();
    }

    private void requestMovieData(BoxOfficeItem boxOffice) {

    }

}
