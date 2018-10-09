package com.cinekancha.actor;

import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.Actor;
import com.cinekancha.entities.model.ActorPhoto;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class ActorPhotoAdapter extends BaseRecyclerAdapter<ActorPhotoHolder> {
    private List<String> actorPhotoList;
    private OnClickListener listener;

    public ActorPhotoAdapter(List<String> actorPhotoList, OnClickListener listener) {
        this.actorPhotoList = actorPhotoList;
        this.listener = listener;
    }

    @Override
    public ActorPhotoHolder onCreateView(int viewType, View view) {
        return new ActorPhotoHolder(this, view, listener);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.adapter_actor_photo
        };
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        ActorPhotoHolder holder = (ActorPhotoHolder) baseHolder;
        String photos = actorPhotoList.get(position);
        Picasso.with(holder.itemView.getContext()).load(photos).into(holder.imgActorPic);
    }

    @Override
    public int getItemCount() {
        return actorPhotoList.size();
    }
}
