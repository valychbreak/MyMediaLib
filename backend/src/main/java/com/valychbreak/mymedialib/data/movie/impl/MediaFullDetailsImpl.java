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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MediaFullDetailsImpl)) return false;

        MediaFullDetailsImpl that = (MediaFullDetailsImpl) o;

        if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (imagePath != null ? !imagePath.equals(that.imagePath) : that.imagePath != null) return false;
        if (stars != null ? !stars.equals(that.stars) : that.stars != null) return false;
        if (reviews != null ? !reviews.equals(that.reviews) : that.reviews != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = genre != null ? genre.hashCode() : 0;
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        result = 31 * result + (stars != null ? stars.hashCode() : 0);
        result = 31 * result + (reviews != null ? reviews.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
