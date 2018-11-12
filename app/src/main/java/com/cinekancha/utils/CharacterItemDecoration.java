package com.cinekancha.utils;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CharacterItemDecoration extends RecyclerView.ItemDecoration {
    private int offset;

    public CharacterItemDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        if (layoutParams.getSpanIndex() % 2 == 0) {
            outRect.bottom = offset;
            outRect.left = offset;
            outRect.right = offset / 2;
            outRect.top = offset;
        } else {
            outRect.right = offset;
            outRect.left = offset / 2;
            outRect.top = offset;
        }
    }
}
