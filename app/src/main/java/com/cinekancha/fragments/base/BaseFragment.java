package com.cinekancha.fragments.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinekancha.bus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Abstract class for fragment view creation
 */
public abstract class BaseFragment extends Fragment {

    public Context mContext;
    private Unbinder mUnbinder;

    protected abstract int getLayoutId();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isButterKnifeUnbind())
            mUnbinder.unbind();
    }

    /**
     * View injections and layout inflation
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    protected boolean isRegisterForRestEvents() {
        return false;
    }

    protected boolean isRegisterForEvents() {
        return false;
    }

    protected boolean isButterKnifeUnbind() {
        return true;
    }

    /**
     * register event
     */
    @Override
    public void onStart() {
        if (isRegisterForEvents())
            EventBus.register(this);
        if (isRegisterForRestEvents())
            EventBus.registerRestObject(this);
        super.onStart();
    }

    /**
     * un-register event
     */
    @Override
    public void onStop() {
        if (isRegisterForEvents())
            EventBus.unregister(this);
        if (isRegisterForRestEvents())
            EventBus.unregisterRestObject(this);
        super.onStop();
    }
}
