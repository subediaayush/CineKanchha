package com.cinekancha.boxOffice;

import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.MovieBoxOffice;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class BoxOfficeAdapter extends BaseRecyclerAdapter<BoxOfficeHolder> {
    private List<MovieBoxOffice> boxOfficeList;
    private OnClickListener listener;

    public BoxOfficeAdapter(List<MovieBoxOffice> boxOfficeList, OnClickListener listener) {
        this.boxOfficeList = boxOfficeList;
        this.listener = listener;
    }

    @Override
    public BoxOfficeHolder onCreateView(int viewType, View view) {
        return new BoxOfficeHolder(this, view, listener);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.adapter_box_office
        };
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        BoxOfficeHolder holder = (BoxOfficeHolder) baseHolder;
        MovieBoxOffice boxOfficeItem = boxOfficeList.get(position);
        
        Float total = boxOfficeItem.getDomestic() + boxOfficeItem.getInternational();
        Float oDay = boxOfficeItem.getOpeningDay();
        Float oWeek = boxOfficeItem.getOpeningWeekend();
        
        holder.total.setText(total + " Crore");
        holder.oDay.setText(oDay + " Crore");
        holder.oWeek.setText(oWeek + " Crore");
        holder.txtMovieName.setText(boxOfficeItem.getMovieName());
        holder.itemView.setOnClickListener(view -> {
            listener.onClick(position);
        });
        Picasso.with(holder.itemView.getContext()).load(Constants.imageUrl + boxOfficeItem.getMovieImage()).into(holder.imgBoxOffice);
    }

    @Override
    public int getItemCount() {
        return boxOfficeList.size();
    }
}
