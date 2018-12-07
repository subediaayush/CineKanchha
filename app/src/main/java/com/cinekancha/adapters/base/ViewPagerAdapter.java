package com.cinekancha.adapters.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paoneking on 6/15/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    public int getFragmentPosition(String title) {
        if (mFragmentTitles.contains(title))
            return mFragmentTitles.indexOf(title);
        return 0;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    public String getTitle(int position){
        return mFragmentTitles.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    /*@Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }*/

    public void clearFragment() {
        mFragments.clear();
        mFragmentTitles.clear();
    }

    public List<Fragment> getFragments() {
        return mFragments;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
