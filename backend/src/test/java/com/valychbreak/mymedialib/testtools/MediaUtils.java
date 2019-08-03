package com.valychbreak.mymedialib.testtools;

import com.uwetrottmann.tmdb2.entities.Media;
import com.valychbreak.mymedialib.controller.api.APIController;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.services.TmdbMediaProvider;
import com.valychbreak.mymedialib.utils.TmdbUtils;

import java.io.IOException;

/**
 * Created by valych on 9/16/17.
 */
public class MediaUtils {
    public static MediaFullDetails getMediaShortDetailsBy(String imdbId) throws IOException {
        Media media = new TmdbMediaProvider(APIController.TMDB_INSTANCE).getMediaBy(imdbId);
        return TmdbUtils.getMediaFullDetailsFromTmdbMedia(APIController.TMDB_INSTANCE, media);
    }
}
