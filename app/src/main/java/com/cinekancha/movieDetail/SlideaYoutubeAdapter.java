package com.cinekancha.movieDetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.entities.model.Links;
import com.cinekancha.fragments.base.BaseFragment;
import com.cinekancha.home.OnSlideClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/13/18.
 */

public class SlideaYoutubeAdapter extends FragmentPagerAdapter {

    private static final int MESSAGE_SLIDE_CHANGE = 123654;
    private static final String TAG = "SlideShowAdapter";

    private final ViewPager mPager;
    private List<Links> linksList;
    private Handler mSlideChangeHandler;

    private OnSlideClickListener mListener;

    @SuppressLint("HandlerLeak")
    public SlideaYoutubeAdapter(FragmentManager fm, ViewPager pager) {
        super(fm);

        linksList = new ArrayList<>();
        mPager = pager;

        mSlideChangeHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_SLIDE_CHANGE: {
                        if (getCount() != 0) {
                            int nextPage = (mPager.getCurrentItem() + 1) % getCount();
                            Log.d(TAG, "Switching page " + nextPage);
                            mPager.setCurrentItem(nextPage);
                        }
                        break;
                    }
                }
            }
        };
    }

    public void setFeaturedItems(List<Links> linksItems) {
        linksList.clear();
        linksList.addAll(linksItems);
        notifyDataSetChanged();
    }

    public void addData(Links item) {
        linksList.add(item);
        notifyDataSetChanged();
    }

    public void startSlideshow() {
        Log.d(TAG, "Resuming slide show");
        if (mSlideChangeHandler.hasMessages(MESSAGE_SLIDE_CHANGE)) {
            Log.d(TAG, "Remove old dispatch");
            mSlideChangeHandler.removeMessages(MESSAGE_SLIDE_CHANGE);
        }
        boolean sent = mSlideChangeHandler.sendEmptyMessageDelayed(MESSAGE_SLIDE_CHANGE, 1000);
        Log.d(TAG, "Slide " + (sent ? "" : " not ") + " resumed");
    }

    public void stopSlideshow() {
        Log.d(TAG, "Pausing slide show");
        mSlideChangeHandler.removeMessages(MESSAGE_SLIDE_CHANGE);
    }

    @Override
    public Fragment getItem(int position) {
        return getFragment(position);
    }

    private Fragment getFragment(int position) {
        return SlideFragment.newInstance(linksList.get(position), mListener);
    }

    @Override
    public int getCount() {
        return linksList.size();
    }

    public void setOnSlideClickListener(OnSlideClickListener onSlideClickListener) {
        this.mListener = onSlideClickListener;
    }

    public static class SlideFragment extends BaseFragment {
        @BindView(R.id.imgBanner)
        protected ImageView mImage;
        @BindView(R.id.txtBannerHead)
        protected TextView mTitle;

        private Links mFeaturedItem;
        private OnSlideClickListener mListener;
        
        public static SlideFragment newInstance(Links item, OnSlideClickListener listener) {
            SlideFragment fragment = new SlideFragment();
            fragment.setFeaturedItem(item);
            fragment.setListener(listener);
            fragment.setArguments(new Bundle());
            return fragment;
        }

        public void setFeaturedItem(Links item) {
            this.mFeaturedItem = item;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            if (!TextUtils.isEmpty(mFeaturedItem.getYoutubeImageUrl())) {
                Log.d("ImageUrl", mFeaturedItem.getYoutubeImageUrl());
                Picasso.with(getContext()).load(mFeaturedItem.getYoutubeImageUrl()).placeholder(R.drawable.placeholder_movie).into(mImage);
            }
            String type = (mFeaturedItem.getType() == null) ? "" : mFeaturedItem.getType();

            mTitle.setText(type);

            if (view != null) {
                view.setOnClickListener(v -> {
                    if (mListener != null) {
                        mListener.onSlideClicked(mFeaturedItem);
                    }
                });
            }

            return view;
        }

        @Override
        protected int getLayoutId() {
            return R.layout.adapter_youtube_pager;
        }
        
        public void setListener(OnSlideClickListener listener) {
            this.mListener = listener;
        }
    }
}
