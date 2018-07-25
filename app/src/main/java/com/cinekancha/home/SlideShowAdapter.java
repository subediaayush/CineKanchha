package com.cinekancha.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.cinekancha.entities.model.FeaturedItem;
import com.cinekancha.fragments.base.BaseFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/13/18.
 */

public class SlideShowAdapter extends FragmentPagerAdapter {
	
	private static final int MESSAGE_SLIDE_CHANGE = 123654;
	private static final String TAG = "SlideShowAdapter";
	
	private final ViewPager mPager;
	private List<FeaturedItem> mFeaturedItems;
	private Handler mSlideChangeHandler;
	
	private OnSlideClickListener mListener;
	
	@SuppressLint("HandlerLeak")
	public SlideShowAdapter(FragmentManager fm, ViewPager pager) {
		super(fm);
		
		mFeaturedItems = new ArrayList<>();
		mPager = pager;
		
		mSlideChangeHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					case MESSAGE_SLIDE_CHANGE: {
						int nextPage = (mPager.getCurrentItem() + 1) % getCount();
						Log.d(TAG, "Switching page " + nextPage);
						mPager.setCurrentItem(nextPage);
						break;
					}
				}
			}
		};
	}
	
	public void setFeaturedItems(List<FeaturedItem> featuredItems) {
		mFeaturedItems.clear();
		mFeaturedItems.addAll(featuredItems);
		notifyDataSetChanged();
	}
	
	public void addData(FeaturedItem item) {
		mFeaturedItems.add(item);
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
		
		private FeaturedItem mFeaturedItem;
		private OnSlideClickListener mListener;
		private int[] colors = new int[] {
				Color.BLUE, Color.RED, Color.YELLOW, Color.GRAY, Color.GREEN
		};
		
		public static SlideFragment newInstance(FeaturedItem item, OnSlideClickListener listener) {
			SlideFragment fragment = new SlideFragment();
			fragment.setFeaturedItem(item);
			fragment.setListener(listener);
			fragment.setArguments(new Bundle());
			return fragment;
		}
		
		public void setFeaturedItem(FeaturedItem item) {
			this.mFeaturedItem = item;
		}
		
		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			View view = super.onCreateView(inflater, container, savedInstanceState);
			
			
			if (!TextUtils.isEmpty(mFeaturedItem.getImageUrl())) {
				Picasso.with(getContext()).load(mFeaturedItem.getImageUrl()).into(mImage);
			}
			
			mImage.setBackgroundColor(getRandomColor());
			
			mTitle.setText(mFeaturedItem.getTitle());
			if (TextUtils.isEmpty(mFeaturedItem.getSubTitle())) {
				mSubtitle.setVisibility(View.GONE);
			} else {
				mSubtitle.setVisibility(View.VISIBLE);
				mSubtitle.setText(mFeaturedItem.getSubTitle());
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
		
		private int getRandomColor() {
			return colors[(int) (Math.random() * colors.length) % colors.length];
		}
		
		public void setListener(OnSlideClickListener listener) {
			this.mListener = listener;
		}
	}
}
