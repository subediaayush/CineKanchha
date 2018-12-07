package com.cinekancha.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EqualSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;
    private int displayMode;
    
    private int spacingPx = -1;

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int GRID = 2;
    
    private boolean left;
    private boolean top;
    private boolean right;
    private boolean bottom;
    
    public EqualSpacingItemDecoration(int spacing) {
        this(spacing, -1);
    }

    public EqualSpacingItemDecoration(int spacing, int displayMode) {
        this.spacing = spacing;
        this.displayMode = displayMode;
    }
    
    public void overrideEdges(boolean left, boolean top, boolean right, boolean bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildViewHolder(view).getAdapterPosition();
        int itemCount = state.getItemCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        setSpacingForDirection(parent.getContext(), outRect, layoutManager, position, itemCount);
    }

    private void setSpacingForDirection(Context context, Rect outRect,
                                        RecyclerView.LayoutManager layoutManager,
                                        int position,
                                        int itemCount) {

        // Resolve display mode automatically
        if (displayMode == -1) {
            displayMode = resolveDisplayMode(layoutManager);
        }
        
        if (spacingPx == -1) {
            spacingPx = ScreenUtils.dpToPx(context, spacing);
        }

        switch (displayMode) {
            case HORIZONTAL:
                outRect.left = spacingPx;
                outRect.right = position == itemCount - 1 || right ? spacingPx : 0;
                outRect.top = top ? spacingPx : 0;
                outRect.bottom = spacingPx;
                break;
            case VERTICAL:
                outRect.left = spacingPx;
                outRect.right = spacingPx;
                outRect.top = top || position != 0 ? spacingPx : 0;
                outRect.bottom = position == itemCount - 1 || bottom ? spacingPx :0;
                break;
            case GRID:
                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    int cols = gridLayoutManager.getSpanCount();
                    int rows = itemCount / cols;
                    if (itemCount % 2 == 1) {
                        rows = rows + 1;
                    }
                    outRect.left = spacingPx;
                    outRect.right = position % cols == cols - 1 ? spacingPx : 0;
                    outRect.top = spacingPx;
                    outRect.bottom = position / cols == rows - 1 ? spacingPx : 0;
                }
                break;
        }
    }

    private int resolveDisplayMode(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) return GRID;
        if (layoutManager.canScrollHorizontally()) return HORIZONTAL;
        return VERTICAL;
    }
}