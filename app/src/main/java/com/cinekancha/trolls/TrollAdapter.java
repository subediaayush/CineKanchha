package com.cinekancha.trolls;

import android.text.TextUtils;
import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.Troll;
import com.cinekancha.entities.model.TrollData;
import com.cinekancha.home.TrollHolder;
import com.cinekancha.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class TrollAdapter extends BaseRecyclerAdapter<TrollHolder> {
    private List<TrollData> mData;

    @Override
    public TrollHolder onCreateView(int viewType, View view) {
        return new TrollHolder(this, view);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.layout_featured_trolls
        };
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        TrollHolder holder = (TrollHolder) baseHolder;
        TrollData troll = mData.get(position);

        if (!TextUtils.isEmpty(troll.getImageUrl())) {
            Picasso.with(baseHolder.itemView.getContext())
                    .load(troll.getImageUrl())
                    .into(holder.troll);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setTrolls(List<TrollData> trolls) {
        this.mData = trolls;
        notifyDataSetChanged();
    }

    public void addTrolls(@NonNull List<TrollData> trolls) {
        if (this.mData == null) this.mData = new ArrayList<>();
        int initial = this.mData.size();
        this.mData.addAll(trolls);
        notifyItemRangeInserted(initial, trolls.size());
    }

    public TrollData getTroll(int position) {
        return mData.get(position % mData.size());
    }
}
