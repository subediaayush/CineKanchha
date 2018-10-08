package com.cinekancha.movieDetail;

import android.text.TextUtils;
import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.Article;
import com.cinekancha.entities.model.Photo;
import com.cinekancha.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class PhotoAdapter extends BaseRecyclerAdapter<PhotosHolder> {
    private List<Photo> photos;

    @Override
    public PhotosHolder onCreateView(int viewType, View view) {
        return new PhotosHolder(this, view);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.adapter_photo
        };
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        PhotosHolder holder = (PhotosHolder) baseHolder;
        Photo photo = photos.get(position);
        if (!TextUtils.isEmpty(photo.getUrl())) {
            String newString = photo.getUrl().replace("\\", "");
            Picasso.with(baseHolder.itemView.getContext())
                    .load(newString)
                    .into(holder.imgPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return photos == null ? 0 : photos.size();
    }

    public void setData(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

}
