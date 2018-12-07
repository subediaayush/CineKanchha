package com.cinekancha.newsGossips;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class NewsGossipHolder extends NewsGossipVH {
    public ImageView imgNewsGossip;
    public TextView txtNewsGossip;

    public NewsGossipHolder(View view) {
        super(view);
        imgNewsGossip = itemView.findViewById(R.id.imgNewsGossip);
        txtNewsGossip = itemView.findViewById(R.id.txtNewsGossip);
    }
}
