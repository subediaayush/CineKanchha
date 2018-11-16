package com.cinekancha.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cinekancha.R;
import com.cinekancha.activities.HomeActivity;
import com.cinekancha.actor.ActorListActivity;
import com.cinekancha.boxOffice.BoxOfficeActivity;
import com.cinekancha.movieReview.ReviewListActivity;
import com.cinekancha.movies.MovieActivity;
import com.cinekancha.newRelease.NewReleaseActivity;
import com.cinekancha.newsGossips.NewsGossipsActivity;
import com.cinekancha.poll.PollsActivity;
import com.cinekancha.trending.FullMoviesActivity;
import com.cinekancha.trending.TrendingActivity;
import com.cinekancha.trivia.TriviaListActivity;
import com.cinekancha.trolls.TrollListActivity;
import com.cinekancha.upcomingMovies.UpcomingMovieActivity;
import com.cinekancha.utils.GlobalUtils;

import butterknife.BindView;


public abstract class BaseNavigationActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        disableNavigationViewScrollbars(navigationView);
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menuHome) {
            GlobalUtils.navigateActivity(this, true, HomeActivity.class);
        } else if (id == R.id.menuNewsGossips) {
            GlobalUtils.navigateActivity(this, !isHomeActivity(), NewsGossipsActivity.class);
        } else if (id == R.id.menuNewRelease) {
            GlobalUtils.navigateActivity(this, !isHomeActivity(), NewReleaseActivity.class);
        } else if (id == R.id.menuUpComing) {
            GlobalUtils.navigateActivity(this, !isHomeActivity(), UpcomingMovieActivity.class);
        } else if (id == R.id.menuAllMovies) {
            GlobalUtils.navigateActivity(this, !isHomeActivity(), MovieActivity.class);
        } else if (id == R.id.menuBoxOffice) {
            GlobalUtils.navigateActivity(this, !isHomeActivity(), BoxOfficeActivity.class);
        } else if (id == R.id.menuTrending) {
            GlobalUtils.navigateActivity(this, !isHomeActivity(), TrendingActivity.class);
        } else if (id == R.id.menuWatchFull) {
            GlobalUtils.navigateActivity(this, !isHomeActivity(), FullMoviesActivity.class);
        } else if (id == R.id.menMovieReviews) {
            GlobalUtils.navigateActivity(this, !isHomeActivity(), ReviewListActivity.class);
        } else if (id == R.id.menuPhotoGallery) {
            GlobalUtils.navigateActivity(this, !isHomeActivity(), ActorListActivity.class);
        } else if (id == R.id.menuFuchePoll) {
            GlobalUtils.navigateActivity(this, !isHomeActivity(), PollsActivity.class);
        } else if (id == R.id.menuFilmyTrivias) {
            GlobalUtils.navigateActivity(this, !isHomeActivity(), TriviaListActivity.class);
        } else if (id == R.id.menuFilmyTroll) {
            GlobalUtils.navigateActivity(this, !isHomeActivity(), TrollListActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected boolean isHomeActivity() {
        return this instanceof HomeActivity;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
