package com.cinekancha.adapters.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Abstract class for easy layout inflate and data binding for list operation adapters
 */
public abstract class BaseRecyclerAdapter<VH extends BaseViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerViewClickListener {

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
        switch (holder.getItemViewType()) {
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
            case 6:
                setViewOfTypeSix(baseHolder, position);
                break;
            case 7:
                setViewOfTypeSeven(baseHolder, position);
                break;
            case 8:
                setViewOfTypeEight(baseHolder, position);
                break;
            case 9:
                setViewOfTypeNine(baseHolder, position);
                break;

            case 10:
                setViewOfTypeTen(baseHolder, position);
                break;

            case 15:
                setViewOfEleven(baseHolder, position);
                break;
            default:
                setViewOfType(baseHolder, position, holder.getItemViewType());
        }
    }

    protected void setViewOfType(BaseViewHolder baseHolder, int position, int viewType) {

    }

    protected void setViewOfEleven(BaseViewHolder baseHolder, int position) {
        setViewOfEleven(baseHolder, position);
    }

    protected void setViewOfTypeNine(BaseViewHolder baseHolder, int position) {
        setViewOfTypeZero(baseHolder, position);
    }

    protected void setViewOfTypeTen(BaseViewHolder baseHolder, int position) {
        setViewOfTypeZero(baseHolder, position);
    }

    protected void setViewOfTypeEight(BaseViewHolder baseHolder, int position) {
        setViewOfTypeZero(baseHolder, position);
    }

    protected void setViewOfTypeSeven(BaseViewHolder baseHolder, int position) {
        setViewOfTypeZero(baseHolder, position);
    }

    protected void setViewOfTypeSix(BaseViewHolder baseHolder, int position) {
        setViewOfTypeZero(baseHolder, position);
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

    public void onClick(View view, int position) {
        if (onClickListener != null) {
            onClickListener.onClick(view, position);
        }
    }

}
