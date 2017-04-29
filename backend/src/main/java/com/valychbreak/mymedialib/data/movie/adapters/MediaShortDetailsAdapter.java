package com.valychbreak.mymedialib.data.movie.adapters;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.impl.MediaShortDetailsImpl;
import com.valychbreak.mymedialib.tools.gson.JsonExclude;

/**
 * Created by Valeriy on 3/18/2017.
 */
public class MediaShortDetailsAdapter extends MediaShortDetailsImpl {
    @JsonExclude
    private OmdbVideoBasic omdbVideoBasic;


    public MediaShortDetailsAdapter(OmdbVideoBasic omdbVideoBasic) {
        super(omdbVideoBasic.getImdbID(), omdbVideoBasic.getTitle());
        this.omdbVideoBasic = omdbVideoBasic;
    }
}
