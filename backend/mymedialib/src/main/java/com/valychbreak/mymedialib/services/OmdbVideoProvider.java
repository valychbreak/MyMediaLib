package com.valychbreak.mymedialib.services;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.model.SearchResults;
import com.omertron.omdbapi.tools.OmdbBuilder;

/**
 * Created by Valeriy on 3/18/2017.
 */
public class OmdbVideoProvider {

    public OmdbVideoFull getOmdbVideo(String imdbId) throws OMDBException {
        OmdbApi api = new OmdbApi();
        OmdbBuilder omdbBuilder = new OmdbBuilder();

        omdbBuilder.setImdbId(imdbId);
        return api.getInfo(omdbBuilder.build());
    }
}
