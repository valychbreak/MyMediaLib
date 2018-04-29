package com.valychbreak.mymedialib.data;

import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaShortDetailsAdapter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by valych on 4/29/17.
 */
public class MediaShortDetailsAdapterTest {
    @Test
    public void testMediaShortDetailsFromOmdbVideoBasic() {
        String imdbId = "imdbId_98765";
        String title = "Some good movie title";

        OmdbVideoBasic omdbVideoBasic = getOmdbVideoBasic(imdbId, title);

        MediaShortDetails mediaShortDetails = new MediaShortDetailsAdapter(omdbVideoBasic);

        Assert.assertEquals(imdbId, mediaShortDetails.getImdbId());
        Assert.assertEquals(title, mediaShortDetails.getTitle());
    }

    private OmdbVideoBasic getOmdbVideoBasic(String imdbId, String title) {
        OmdbVideoBasic omdbVideoBasic = new OmdbVideoBasic();

        omdbVideoBasic.setImdbID(imdbId);
        omdbVideoBasic.setTitle(title);
        return omdbVideoBasic;
    }
}
