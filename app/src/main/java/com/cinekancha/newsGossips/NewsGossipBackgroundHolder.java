package com.cinekancha.newsGossips;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class NewsGossipBackgroundHolder extends NewsGossipVH {
    public ImageView imgNewsGossip;
    public TextView txtNewsGossip;
    public View divider;

    public NewsGossipBackgroundHolder(View view) {
        super(view);
        imgNewsGossip = itemView.findViewById(R.id.imgNewsGossip);
        txtNewsGossip = itemView.findViewById(R.id.txtNewsGossip);
        divider = itemView.findViewById(R.id.divider);
    }
}
