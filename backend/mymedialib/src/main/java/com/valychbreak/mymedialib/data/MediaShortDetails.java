package com.valychbreak.mymedialib.data;

/**
 * Created by Valeriy on 3/18/2017.
 */
public interface MediaShortDetails {
    String getImdbId();
    String getTitle();
    boolean isFavourite();
    void setFavourite(boolean isFavourite);
}
