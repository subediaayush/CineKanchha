package com.cinekancha.actor;

import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.Video;
import com.cinekancha.entities.model.Actor;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class ActorAdapter extends BaseRecyclerAdapter<ActorHolder> {
    private List<Actor> actorList;
    private OnClickListener listener;

    public ActorAdapter(List<Actor> actorList, OnClickListener listener) {
        this.actorList = actorList;
        this.listener = listener;
    }

    @Override
    public ActorHolder onCreateView(int viewType, View view) {
        return new ActorHolder(this, view, listener);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.adapter_actor
        };
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        ActorHolder holder = (ActorHolder) baseHolder;
        Actor actor = actorList.get(position);
        holder.txtActor.setText(actor.getName());
        Picasso.with(holder.itemView.getContext()).load(Constants.imageUrl + actor.getImageUrl()).into(holder.imgActor);
        holder.itemView.setOnClickListener(view -> {
            listener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return actorList.size();
    }
}
