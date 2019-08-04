package com.valychbreak.mymedialib.testtools;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.Media;
import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.services.TmdbMediaProvider;
import com.valychbreak.mymedialib.services.utils.TmdbService;
import com.valychbreak.mymedialib.utils.TmdbUtils;

import java.io.IOException;

/**
 * Created by valych on 9/16/17.
 */
public class MediaUtils {
    public static MediaFullDetails getMediaShortDetailsBy(String imdbId) throws IOException {
        Tmdb tmdbInstance = APIController.TMDB_INSTANCE;

        Media media = new TmdbMediaProvider(tmdbInstance, new TmdbService(tmdbInstance)).getMediaBy(imdbId);
        return TmdbUtils.getMediaFullDetailsFromTmdbMedia(tmdbInstance, media);
    }
}
