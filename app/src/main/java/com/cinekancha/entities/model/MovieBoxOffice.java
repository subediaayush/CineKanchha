package com.cinekancha.entities.model;

public class MovieBoxOffice {
    private BoxOfficeItem boxOfficeItem;

    private MovieDetail movieDetail;

    public BoxOfficeItem getBoxOfficeItem() {
        return boxOfficeItem;
    }

    public void setBoxOfficeItem(BoxOfficeItem boxOfficeItem) {
        this.boxOfficeItem = boxOfficeItem;
    }

    public MovieDetail getMovieDetail() {
        return movieDetail;
    }

    public void setMovieDetail(MovieDetail movieDetail) {
        this.movieDetail = movieDetail;
    }
}
