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
import com.cinekancha.boxOffice.BoxOfficeActivity;
import com.cinekancha.movies.MovieActivity;
import com.cinekancha.newRelease.NewReleaseActivity;
import com.cinekancha.newsGossips.NewsGossipsActivity;
import com.cinekancha.trolls.TrollListActivity;
import com.cinekancha.utils.GlobalUtils;

import butterknife.BindView;


public abstract class BaseNavigationActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

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
            // Handle the camera action
        } else if (id == R.id.menuNewsGossips) {
            GlobalUtils.navigateActivity(this, true, NewsGossipsActivity.class);
        } else if (id == R.id.menuNewRelease) {
            GlobalUtils.navigateActivity(this, true, NewReleaseActivity.class);
        } else if (id == R.id.menuUpComing) {
            GlobalUtils.navigateActivity(this, true, MovieActivity.class);
        } else if (id == R.id.menuBoxOffice) {
            GlobalUtils.navigateActivity(this, true, BoxOfficeActivity.class);

        } else if (id == R.id.menuTrending) {

        } else if (id == R.id.menuWatchFull) {

        } else if (id == R.id.menMovieReviews) {

        } else if (id == R.id.menuPhotoGallery) {

        } else if (id == R.id.menuFuchePoll) {

        } else if (id == R.id.menuFilmyTrivias) {

        } else if (id == R.id.menuFilmyTroll) {
            GlobalUtils.navigateActivity(this, true, TrollListActivity.class);
        } else if (id == R.id.menuSettings) {

        } else if (id == R.id.menuContact) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initToolbar(String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.movies);
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
