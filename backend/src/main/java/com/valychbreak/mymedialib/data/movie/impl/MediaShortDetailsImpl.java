package com.valychbreak.mymedialib.data.movie.impl;

import com.valychbreak.mymedialib.data.movie.MediaShortDetails;

/**
 * Created by valych on 4/29/17.
 */
public class MediaShortDetailsImpl implements MediaShortDetails {
    protected String imdbId;
    protected String title;
    protected boolean isFavourite;

    public MediaShortDetailsImpl() {
    }

    public MediaShortDetailsImpl(String imdbId, String title) {
        this.imdbId = imdbId;
        this.title = title;
    }

    public MediaShortDetailsImpl(String imdbId, String title, boolean isFavourite) {
        this.imdbId = imdbId;
        this.title = title;
        this.isFavourite = isFavourite;
    }

    @Override
    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean isFavourite() {
        return isFavourite;
    }

    @Override
    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
