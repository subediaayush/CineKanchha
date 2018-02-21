package com.cinekancha.adapters.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import butterknife.ButterKnife;

/**
 * Abstract class for easy layout inflate and data binding for list operation adapters
 */
public abstract class BaseRecyclerAdapter<VH extends BaseRecyclerAdapter.BaseViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private RecyclerViewClickListener onClickListener;
    private int lastPosition = -1;

    protected Context getContext() {
        return mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return createView(viewType, LayoutInflater.from(mContext).inflate(getLayoutsForViewType()[viewType], parent, false));
    }

    public abstract VH onCreateView(int viewType, View view);

    final VH createView(int viewType, View view) {
        return onCreateView(viewType, view);
    }

    public abstract int[] getLayoutsForViewType();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder baseHolder = (BaseViewHolder) holder;
        switch (getItemViewType(position)) {
            case 0:
                setViewOfTypeZero(baseHolder, position);
                break;
            case 1:
                setViewOfTypeOne(baseHolder, position);
                break;
            case 2:
                setViewOfTypeTwo(baseHolder, position);
                break;
            case 3:
                setViewOfTypeThree(baseHolder, position);
                break;
            case 4:
                setViewOfTypeFour(baseHolder, position);
                break;
            case 5:
                setViewOfTypeFive(baseHolder, position);
                break;
        }

    }

    protected void setViewOfTypeFive(BaseViewHolder baseHolder, int position) {
        setViewOfTypeZero(baseHolder, position);
    }

    protected void setViewOfTypeFour(BaseViewHolder baseHolder, int position) {
        setViewOfTypeZero(baseHolder, position);
    }

    protected void setViewOfTypeThree(BaseViewHolder baseHolder, int position) {
        setViewOfTypeZero(baseHolder, position);
    }

    protected void setViewOfTypeTwo(BaseViewHolder baseHolder, int position) {
        setViewOfTypeZero(baseHolder, position);
    }

    protected void setViewOfTypeOne(BaseViewHolder baseHolder, int position) {
        setViewOfTypeZero(baseHolder, position);
    }

    protected abstract void setViewOfTypeZero(BaseViewHolder baseHolder, int position);

    public void setOnClickListener(RecyclerViewClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    protected void setAnimation(View viewToAnimate, int anim, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, anim);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    /**
     * Interface definition for a callback to be invoked when an item in a
     * RecyclerView has been clicked.
     */
    public interface RecyclerViewClickListener {

        /**
         * Callback method to be invoked when a item in a
         * RecyclerView is clicked
         *
         * @param v        The view within the RecyclerView.Adapter
         * @param position The position of the view in the adapter
         */
        void onClick(View v, int position);
    }

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public BaseViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            for (int clickViewId : getClickViewIdList()) {
                view.findViewById(clickViewId).setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            if (onClickListener != null) {
                onClickListener.onClick(view, getLayoutPosition());
            }
        }

        public abstract int[] getClickViewIdList();
    }
}
