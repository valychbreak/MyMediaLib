package com.valychbreak.mymedialib.testtools;

import com.uwetrottmann.tmdb2.entities.Media;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.exception.ExternalAPIException;
import com.valychbreak.mymedialib.services.TmdbMediaProvider;
import com.valychbreak.mymedialib.services.utils.TmdbService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MediaUtils {
    private TmdbService tmdbService;
    private TmdbMediaProvider tmdbMediaProvider;

    public MediaUtils(TmdbService tmdbService, TmdbMediaProvider tmdbMediaProvider) {
        this.tmdbService = tmdbService;
        this.tmdbMediaProvider = tmdbMediaProvider;
    }

    public MediaFullDetails getMediaFullDetailsBy(String imdbId) throws IOException, ExternalAPIException {
        Media media = tmdbMediaProvider.getMediaBy(imdbId);
        return tmdbService.getMediaDetails(media)
                .orElseThrow(() -> new IllegalArgumentException("Media not found by imdb ID: " + imdbId));
    }
}
