package com.valychbreak.mymedialib.data.movie;

/**
 * Created by Valeriy on 3/18/2017.
 */
public interface MediaFullDetails extends MediaShortDetails {
    String getImdbId();
    String getDuration();
    String getDescription();
    String getImagePath();
    String getStars();
    String getReviews();
    String getTitle();
    String getGenre();
    String getType();
}
