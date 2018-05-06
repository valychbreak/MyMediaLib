package com.valychbreak.mymedialib.data.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Valeriy on 3/18/2017.
 */
public interface MediaShortDetails {
    String getImdbId();
    String getTitle();

    // TODO: remove this from domain object. Use DTO object in controllers
    @JsonProperty(value="isFavourite")
    boolean isFavourite();

    void setFavourite(boolean isFavourite);
}
