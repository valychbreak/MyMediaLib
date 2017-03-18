package com.valychbreak.mymedialib.tools.adapters;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.valychbreak.mymedialib.data.MediaShortDetails;

/**
 * Created by Valeriy on 3/18/2017.
 */
public class MediaShortDetailsAdapter implements MediaShortDetails {
    private OmdbVideoBasic omdbVideoBasic;

    public MediaShortDetailsAdapter(OmdbVideoBasic omdbVideoBasic) {
        this.omdbVideoBasic = omdbVideoBasic;
    }

    @Override
    public String getImdbId() {
        return omdbVideoBasic.getImdbID();
    }

    @Override
    public String getTitle() {
        return omdbVideoBasic.getTitle();
    }
}
