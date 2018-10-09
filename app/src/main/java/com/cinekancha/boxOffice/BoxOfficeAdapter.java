package com.cinekancha.boxOffice;

import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.entities.model.BoxOfficeItem;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movies.MovieHolder;
import com.cinekancha.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class BoxOfficeAdapter extends BaseRecyclerAdapter<BoxOfficeHolder> {
    private List<BoxOfficeItem> boxOfficeList;
    private OnClickListener listener;

    public BoxOfficeAdapter(List<BoxOfficeItem> boxOfficeList, OnClickListener listener) {
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
        BoxOfficeItem boxOfficeItem = boxOfficeList.get(position);
        holder.txtGross.setText(boxOfficeItem.getTotalCollected() + " Crore");
        holder.txtWeekend.setText(boxOfficeItem.getTotalCollected()+ " Crore");
        holder.txtMovieName.setText(boxOfficeItem.getMovieName());
        Picasso.with(holder.itemView.getContext()).load(Constants.imageUrl + boxOfficeItem.getMovieImage()).into(holder.imgBoxOffice);
    }

    @Override
    public int getItemCount() {
        return boxOfficeList.size();
    }
}
