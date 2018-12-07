package com.cinekancha.utils;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public ItemOffsetDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildViewHolder(view).getAdapterPosition();
        int itemCount = state.getItemCount();
        
        int cols = 1;
        int rows = itemCount;
    
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            cols = gridLayoutManager.getSpanCount();
            rows = itemCount / cols;
            if (itemCount % 2 == 1) {
                rows = rows + 1;
            }
        }
        outRect.left = mItemOffset;
        outRect.right = position % cols == cols - 1 ? mItemOffset : 0;
        outRect.top = mItemOffset;
        outRect.bottom = position / cols == rows - 1 ? mItemOffset : 0;
    }
}