package com.cinekancha.home;

import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.adapters.base.BaseViewHolder;
import com.cinekancha.adapters.base.RecyclerViewClickListener;
import com.cinekancha.entities.model.MovieBoxOffice;
import com.cinekancha.utils.ListUtils;

import java.util.List;

import butterknife.BindView;

public class FeatureBoxOfficeAdapter extends BaseRecyclerAdapter<FeatureBoxOfficeAdapter.BoxOfficeItemHolder> {

    private List<MovieBoxOffice> items;

    @Override
    public BoxOfficeItemHolder onCreateView(int viewType, View view) {
        return new BoxOfficeItemHolder(this, view);
    }

    @Override
    public int[] getLayoutsForViewType() {
        return new int[]{
                R.layout.layout_featued_box_office
        };
    }

    @Override
    protected void setViewOfTypeZero(BaseViewHolder baseHolder, int position) {
        BoxOfficeItemHolder holder = (BoxOfficeItemHolder) baseHolder;
        MovieBoxOffice item = items.get(position);
        holder.movie.setText(item.getMovieName());
        holder.sum.setText(MovieBoxOffice.formatCollection(item.getCategory(), item.getInternational() + item.getDomestic()));
    }

    @Override
    public int getItemCount() {
        return ListUtils.getSize(items);
    }

    public void setBoxOfficeItems(List<MovieBoxOffice> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class BoxOfficeItemHolder extends BaseViewHolder {

        @BindView(R.id.movie)
        public TextView movie;

        @BindView(R.id.sum)
        public TextView sum;

        public BoxOfficeItemHolder(RecyclerViewClickListener baseRecyclerAdapter, View view) {
            super(baseRecyclerAdapter, view);

        }

        @Override
        public int[] getClickViewIdList() {
            return new int[0];
        }
    }
}
