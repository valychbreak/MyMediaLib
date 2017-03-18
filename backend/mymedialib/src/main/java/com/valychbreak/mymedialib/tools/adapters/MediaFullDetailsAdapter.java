package com.valychbreak.mymedialib.tools.adapters;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.valychbreak.mymedialib.data.MediaFullDetails;

/**
 * Created by Valeriy on 3/18/2017.
 */
public class MediaFullDetailsAdapter extends MediaShortDetailsAdapter implements MediaFullDetails {
    private OmdbVideoFull omdbVideoFull;


    public MediaFullDetailsAdapter(OmdbVideoFull omdbVideoFull) {
        super(omdbVideoFull);
        this.omdbVideoFull = omdbVideoFull;
    }

    @Override
    public String getGenre() {
        return omdbVideoFull.getGenre();
    }

    @Override
    public String getType() {
        return omdbVideoFull.getType();
    }
}
