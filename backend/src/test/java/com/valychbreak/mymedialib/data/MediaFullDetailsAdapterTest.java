package com.valychbreak.mymedialib.data;

import com.omertron.omdbapi.model.OmdbVideoFull;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsAdapter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by valych on 4/29/17.
 */
public class MediaFullDetailsAdapterTest {
    @Test
    public void testMediaFullDetailsFromOmdbVideoFull() {
        String description = "movieDesc";
        String duration = "movieDuration";
        String genre = "movieGenre";
        String imagePath = "imagePath";
        String imdbId = "imdbId_12345";
        String reviews = "movieReviews";
        String stars = "5";
        String title = "Fancy movie title";
        String type = "Movie";

        OmdbVideoFull omdbVideoFull = getOmdbVideoFull(description, duration, genre, imagePath, imdbId, reviews, stars, title, type);
        MediaFullDetails mediaFullDetails = new MediaFullDetailsAdapter(omdbVideoFull);

        Assert.assertEquals(description, mediaFullDetails.getDescription());
        Assert.assertEquals(duration, mediaFullDetails.getDuration());
        Assert.assertEquals(genre, mediaFullDetails.getGenre());
        Assert.assertEquals(imagePath, mediaFullDetails.getImagePath());
        Assert.assertEquals(imdbId, mediaFullDetails.getImdbId());
        Assert.assertEquals(reviews, mediaFullDetails.getReviews());
        Assert.assertEquals(stars, mediaFullDetails.getStars());
        Assert.assertEquals(title, mediaFullDetails.getTitle());
        Assert.assertEquals(type, mediaFullDetails.getType());

    }

    private OmdbVideoFull getOmdbVideoFull(String description, String duration, String genre, String imagePath, String imdbId, String reviews, String stars, String title, String type) {
        OmdbVideoFull omdbVideoFull = new OmdbVideoFull();
        omdbVideoFull.setTitle(title);
        omdbVideoFull.setType(type);
        omdbVideoFull.setPlot(description);
        omdbVideoFull.setRuntime(duration);
        omdbVideoFull.setGenre(genre);
        omdbVideoFull.setPoster(imagePath);
        omdbVideoFull.setImdbID(imdbId);
        omdbVideoFull.setTomatoReviews(reviews);
        omdbVideoFull.setImdbRating(stars);
        return omdbVideoFull;
    }
}
