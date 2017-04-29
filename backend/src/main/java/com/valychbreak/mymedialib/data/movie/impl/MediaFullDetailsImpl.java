package com.valychbreak.mymedialib.data.movie.impl;

import com.valychbreak.mymedialib.data.movie.MediaFullDetails;

/**
 * Created by valych on 4/29/17.
 */
public class MediaFullDetailsImpl extends MediaShortDetailsImpl implements MediaFullDetails {
    protected String genre;
    protected String duration;
    protected String description;
    protected String imagePath;
    protected String stars;
    protected String reviews;
    protected String type;

    public MediaFullDetailsImpl() {
    }

    @Override
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    @Override
    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
