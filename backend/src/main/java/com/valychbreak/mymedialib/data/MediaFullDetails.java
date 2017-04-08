package com.valychbreak.mymedialib.data;

/**
 * Created by Valeriy on 3/18/2017.
 */
public interface MediaFullDetails extends MediaShortDetails {
    String getImdbId();
    String getTitle();
    String getGenre();
    String getType();
}
