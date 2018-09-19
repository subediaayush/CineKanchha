package com.cinekancha.home;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinekancha.adapters.base.RecyclerViewClickListener;
import com.cinekancha.entities.ThumbWrapper;
import com.cinekancha.entities.ThumbnailConverter;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.Constants;
import com.cinekancha.utils.ListUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayushsubedi on 3/15/18.
 */

public class ThumbnailAdapter<T> extends RecyclerView.Adapter<ThumbnailHolder> implements RecyclerViewClickListener {

    private final int mItemLayout;
    private final List<ThumbWrapper> mThumnails = new ArrayList<>();
    private final ThumbnailConverter<T> mConverter;
    private final OnClickListener listener;

    public ThumbnailAdapter(int layout, ThumbnailConverter<T> converter, OnClickListener listener) {
        this.mItemLayout = layout;
        this.mConverter = converter;
        this.listener = listener;
    }


    @Override
    public ThumbnailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(mItemLayout, parent, false);
        return new ThumbnailHolder(this, view);
    }

    @Override
    public void onBindViewHolder(ThumbnailHolder holder, int position) {
        ThumbWrapper thumb = mThumnails.get(position);
        if (holder.title != null) holder.title.setText(thumb.getCaption());
        if (holder.subTitle != null) holder.subTitle.setText(thumb.getSubCaption());
        if (holder.thumbnail != null) {
            if (thumb.getImageRes() != -1) {
                Picasso.with(holder.thumbnail.getContext())
                        .load(Constants.imageUrl + thumb.getImageRes())
                        .into(holder.thumbnail);
            } else if (!TextUtils.isEmpty(thumb.getImageUrl())) {
                String newString = thumb.getImageUrl().replace("\\", "");
                Picasso.with(holder.thumbnail.getContext())
                        .load(Constants.imageUrl + newString)
                        .into(holder.thumbnail);
            } else {
                holder.thumbnail.setImageDrawable(new ColorDrawable(0x77777777));
            }
        }
        holder.itemView.setOnClickListener(view -> listener.onClick(thumb.getId()));
    }

    @Override
    public int getItemCount() {
        return ListUtils.getSize(mThumnails);
    }

    public void setThumbnails(List<T> data) {
        mThumnails.clear();
        for (T datum : data) {
            mThumnails.add(mConverter.convert(datum));
        }
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v, int position) {

    }
}
