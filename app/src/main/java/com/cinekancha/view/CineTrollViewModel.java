package com.cinekancha.view;

import android.app.Application;
import androidx.annotation.NonNull;

import com.cinekancha.entities.model.TrollData;

import java.util.List;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class CineTrollViewModel extends BasePaginationViewModel {

    private List<TrollData> trollDataList;
    private List<TrollData> appendTrollDataList;

    public CineTrollViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void resetState() {
        trollDataList = null;
        appendTrollDataList = null;
    }

    public List<TrollData> getTrollDataList() {
        return trollDataList;
    }

    public void setTrollDataList(List<TrollData> trollDataList) {
        if (this.trollDataList == null)
            this.trollDataList = trollDataList;
        else
            this.trollDataList.addAll(trollDataList);
    }

    public List<TrollData> getAppendTrollDataList() {
        return appendTrollDataList;
    }

    public void setAppendTrollDataList(List<TrollData> appendTrollDataList) {
        this.appendTrollDataList = appendTrollDataList;
    }
}
