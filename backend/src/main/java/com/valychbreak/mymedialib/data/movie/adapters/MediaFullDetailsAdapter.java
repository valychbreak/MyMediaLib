package com.valychbreak.mymedialib.data.movie.adapters;

import com.omertron.omdbapi.model.OmdbVideoFull;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.utils.gson.JsonExclude;

/**
 * Created by Valeriy on 3/18/2017.
 */
public class MediaFullDetailsAdapter extends MediaFullDetailsImpl implements MediaFullDetails {
    @JsonExclude
    private OmdbVideoFull omdbVideoFull;

    public MediaFullDetailsAdapter(OmdbVideoFull omdbVideoFull) {
        this.omdbVideoFull = omdbVideoFull;
        setup(omdbVideoFull);
    }

    private void setup(OmdbVideoFull omdbVideoFull) {
        imdbId = omdbVideoFull.getImdbID();
        title = omdbVideoFull.getTitle();
        genre = omdbVideoFull.getGenre();
        duration = omdbVideoFull.getRuntime();
        description = omdbVideoFull.getPlot();
        imagePath = omdbVideoFull.getPoster();
        stars = omdbVideoFull.getImdbRating();
        reviews = omdbVideoFull.getTomatoReviews();
        type = omdbVideoFull.getType();
    }
}
