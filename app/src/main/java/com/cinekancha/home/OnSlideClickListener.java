package com.cinekancha.home;

import com.cinekancha.entities.model.FeaturedContent;
import com.cinekancha.entities.model.Links;

/**
 * Created by aayushsubedi on 3/13/18.
 */

public interface OnSlideClickListener {
    void onSlideClicked(FeaturedContent item);

    void onSlideClicked(Links item);
}
