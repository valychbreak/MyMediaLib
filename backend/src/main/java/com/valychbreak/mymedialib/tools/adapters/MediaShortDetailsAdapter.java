package com.valychbreak.mymedialib.tools.adapters;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.valychbreak.mymedialib.data.MediaShortDetails;
import com.valychbreak.mymedialib.tools.gson.JsonExclude;

/**
 * Created by Valeriy on 3/18/2017.
 */
public class MediaShortDetailsAdapter implements MediaShortDetails {
    @JsonExclude
    private OmdbVideoBasic omdbVideoBasic;

    protected String imdbId;
    protected String title;
    protected boolean isFavourite;

    public MediaShortDetailsAdapter(OmdbVideoBasic omdbVideoBasic) {
        this.omdbVideoBasic = omdbVideoBasic;
        setup(omdbVideoBasic);
    }

    private void setup(OmdbVideoBasic omdbVideoBasic) {
        imdbId = omdbVideoBasic.getImdbID();
        title = omdbVideoBasic.getTitle();
    }
    @Override
    public String getImdbId() {
        return imdbId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
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
