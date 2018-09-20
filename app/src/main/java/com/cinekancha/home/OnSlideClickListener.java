package com.cinekancha.home;

import com.cinekancha.entities.model.FeaturedItem;
import com.cinekancha.entities.model.Links;

/**
 * Created by aayushsubedi on 3/13/18.
 */

public interface OnSlideClickListener {
    void onSlideClicked(FeaturedItem item);

    void onSlideClicked(Links item);
}
