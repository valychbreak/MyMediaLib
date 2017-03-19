package com.valychbreak.mymedialib.tools.adapters;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.valychbreak.mymedialib.data.MediaFullDetails;
import com.valychbreak.mymedialib.tools.gson.JsonExclude;

/**
 * Created by Valeriy on 3/18/2017.
 */
public class MediaFullDetailsAdapter extends MediaShortDetailsAdapter implements MediaFullDetails {
    @JsonExclude
    private OmdbVideoFull omdbVideoFull;

    protected String genre;
    protected String duration;
    protected String description;
    protected String imagePath;
    protected String stars;
    protected String reviews;


    public MediaFullDetailsAdapter(OmdbVideoFull omdbVideoFull) {
        super(omdbVideoFull);
        this.omdbVideoFull = omdbVideoFull;
        setup(omdbVideoFull);
    }

    private void setup(OmdbVideoFull omdbVideoFull) {
        genre = omdbVideoFull.getGenre();
        duration = omdbVideoFull.getRuntime();
        description = omdbVideoFull.getPlot();
        imagePath = omdbVideoFull.getPoster();
        stars = omdbVideoFull.getImdbRating();
        reviews = omdbVideoFull.getTomatoReviews();
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
