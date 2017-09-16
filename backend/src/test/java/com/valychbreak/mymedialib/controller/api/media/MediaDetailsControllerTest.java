package com.valychbreak.mymedialib.controller.api.media;

import com.valychbreak.mymedialib.controller.AbstractControllerTest;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.services.TmdbMediaProvider;
import com.valychbreak.mymedialib.testtools.MediaUtils;
import com.valychbreak.mymedialib.utils.TmdbUtils;
import com.valychbreak.mymedialib.utils.gson.GsonBuilderTools;
import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by valych on 9/16/17.
 */
// TODO: use manually created MediaFullDetails objects to compare with API ones
public class MediaDetailsControllerTest extends AbstractControllerTest {
    @Test
    public void getMediaDetailsByImdbIdOfMovie() throws Exception {
        MediaFullDetails fightClubMovie = MediaUtils.getMediaShortDetailsBy("tt0137523");

        mockMvc.perform(get("/api/media/details/tt0137523"))
                .andExpect(status().isOk())
                .andExpect(content().json(json(fightClubMovie)));
    }

    @Test
    public void getMediaDetailsByImdbIdOfTvShow() throws Exception {
        MediaFullDetails friendsTVSeries = MediaUtils.getMediaShortDetailsBy("tt0108778");

        mockMvc.perform(get("/api/media/details/tt0108778"))
                .andExpect(status().isOk())
                .andExpect(content().json(json(friendsTVSeries)));
    }
}