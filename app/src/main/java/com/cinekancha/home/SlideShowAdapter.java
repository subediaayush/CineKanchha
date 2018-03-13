package com.cinekancha.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.entities.model.FeaturedItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/13/18.
 */

public class SlideShowAdapter extends FragmentStatePagerAdapter {
	
	private static final int MESSAGE_SLIDE_CHANGE = 123654;
	
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
						mPager.setCurrentItem(mPager.getCurrentItem() % getCount());
						startSlideshow();
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
		mSlideChangeHandler.sendEmptyMessageDelayed(MESSAGE_SLIDE_CHANGE, 5000);
	}
	
	public void stopSlideshow() {
		mSlideChangeHandler.removeMessages(MESSAGE_SLIDE_CHANGE);
	}
	
	@Override
	public Fragment getItem(int position) {
		return getFragment(position);
	}
	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
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
	
	public static class SlideFragment extends Fragment {
		private FeaturedItem mFeaturedItem;
		
		@BindView(R.id.image)
		protected ImageView mImage;
		@BindView(R.id.description)
		protected TextView mDesc;
		private OnSlideClickListener mListener;
		
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
			View view = inflater.inflate(R.layout.fragment_featured_slide, container, false);
			
			if (!TextUtils.isEmpty(mFeaturedItem.getImageUrl())) {
				Picasso.with(getContext()).load(mFeaturedItem.getImageUrl()).into(mImage);
			}
			
			mDesc.setText(mFeaturedItem.getDescription());
			
			view.setOnClickListener(v -> {
				if (mListener != null) {
					mListener.onSlideClicked(mFeaturedItem);
				}
			});
			
			return view;
		}
		
		public void setListener(OnSlideClickListener listener) {
			this.mListener = listener;
		}
	}
}
