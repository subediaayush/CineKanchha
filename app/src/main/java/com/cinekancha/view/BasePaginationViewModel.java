package com.cinekancha.view;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

public abstract class BasePaginationViewModel extends AndroidViewModel {
    // Sets the starting page index
    public final int startingPageIndex = 1;
    private int currentPage = startingPageIndex;
    private boolean toAppend = false;
    private int lastPage;

    public BasePaginationViewModel(@NonNull Application application) {
        super(application);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isToAppend() {
        return toAppend;
    }

    public void setToAppend(boolean toAppend) {
        this.toAppend = toAppend;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public abstract void resetState();
}
