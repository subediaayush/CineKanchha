package com.cinekancha.view;

import android.app.Application;
import android.support.annotation.NonNull;

import com.cinekancha.entities.model.ReviewData;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineReviewViewModel extends BasePaginationViewModel {
    private List<ReviewData> reviewDataList;
    private List<ReviewData> appendReviewDataList;

    public CineReviewViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void resetState() {
        reviewDataList = null;
        appendReviewDataList = null;
    }

    public List<ReviewData> getReviewDataList() {
        return reviewDataList;
    }

    public void setReviewDataList(List<ReviewData> reviewDataList) {
        if (this.reviewDataList == null) {
            this.reviewDataList = reviewDataList;
        } else this.reviewDataList.addAll(reviewDataList);
    }

    public List<ReviewData> getAppendReviewDataList() {
        return appendReviewDataList;
    }

    public void setAppendReviewDataList(List<ReviewData> appendReviewDataList) {
        this.appendReviewDataList = appendReviewDataList;
    }
}
