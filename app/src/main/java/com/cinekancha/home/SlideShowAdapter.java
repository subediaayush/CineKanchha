package com.cinekancha.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.entities.model.FeaturedContent;
import com.cinekancha.fragments.base.BaseFragment;
import com.cinekancha.utils.Constants;
import com.cinekancha.view.ParallaxPagerTransformer;
import com.cinekancha.view.ViewPagerCustomDuration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/13/18.
 */

public class SlideShowAdapter extends FragmentStatePagerAdapter {

    private static final int MESSAGE_SLIDE_CHANGE = 123654;
    private static final String TAG = "SlideShowAdapter";

    private final ViewPagerCustomDuration mPager;
    private List<FeaturedContent> mFeaturedItems;
    private Handler mSlideChangeHandler;

    private OnSlideClickListener mListener;

    @SuppressLint("HandlerLeak")
    public SlideShowAdapter(FragmentManager fm, ViewPager pager) {
        super(fm);

        mFeaturedItems = new ArrayList<>();
        mPager = (ViewPagerCustomDuration) pager;
        ParallaxPagerTransformer transformer = new ParallaxPagerTransformer(R.id.image);
        transformer.setSpeed(0.1f);
        pager.setPageTransformer(false, transformer);

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

    public void setFeaturedItems(List<FeaturedContent> featuredItems) {
        mFeaturedItems.clear();
        mFeaturedItems.addAll(featuredItems);
        notifyDataSetChanged();
    }

    public void addData(FeaturedContent item) {
        mFeaturedItems.add(item);
        notifyDataSetChanged();
    }

    public void startSlideshow() {
        Log.d(TAG, "Resuming slide show");
        if (mSlideChangeHandler.hasMessages(MESSAGE_SLIDE_CHANGE)) {
            Log.d(TAG, "Remove old dispatch");
            mSlideChangeHandler.removeMessages(MESSAGE_SLIDE_CHANGE);
        }
        boolean sent = mSlideChangeHandler.sendEmptyMessageDelayed(MESSAGE_SLIDE_CHANGE, 3000);
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
        return SlideFragment.newInstance(mFeaturedItems.get(position), mListener);
    }

    @Override
    public int getCount() {
        return mFeaturedItems.size();
    }

    public void setOnSlideClickListener(OnSlideClickListener onSlideClickListener) {
        this.mListener = onSlideClickListener;
    }

    public static class SlideFragment extends BaseFragment {
        @BindView(R.id.image)
        protected ImageView mImage;
        @BindView(R.id.title)
        protected TextView mTitle;
        @BindView(R.id.subtitle)
        protected TextView mSubtitle;

        private FeaturedContent mFeaturedItem;
        private OnSlideClickListener mListener;

        public static SlideFragment newInstance(FeaturedContent item, OnSlideClickListener listener) {
            SlideFragment fragment = new SlideFragment();
            fragment.setFeaturedItem(item);
            fragment.setListener(listener);
            fragment.setArguments(new Bundle());
            return fragment;
        }

        public void setFeaturedItem(FeaturedContent item) {
            this.mFeaturedItem = item;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);

            if (mFeaturedItem == null) return view;
            if (!TextUtils.isEmpty(mFeaturedItem.getImageUrl())) {
                String newString = mFeaturedItem.getImageUrl().replace("\\", "");
                Picasso.with(getContext()).load(Constants.imageUrl + newString).into(mImage);
            }

            mTitle.setText(mFeaturedItem.getTitle());
            if (TextUtils.isEmpty(mFeaturedItem.getSubtitle())) {
                mSubtitle.setVisibility(View.GONE);
            } else {
                mSubtitle.setVisibility(View.VISIBLE);
                mSubtitle.setText(mFeaturedItem.getSubtitle());
            }

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
            return R.layout.fragment_featured_slide;
        }

        public void setListener(OnSlideClickListener listener) {
            this.mListener = listener;
        }
    }
}
