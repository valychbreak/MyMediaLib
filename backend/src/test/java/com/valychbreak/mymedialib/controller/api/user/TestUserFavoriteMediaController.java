package com.valychbreak.mymedialib.controller.api.user;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.valychbreak.mymedialib.controller.AbstractControllerTest;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCatalog;
import com.valychbreak.mymedialib.repository.UserMediaCatalogRepository;
import com.valychbreak.mymedialib.services.OmdbVideoProvider;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsAdapter;
import com.valychbreak.mymedialib.testtools.MediaUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by valych on 4/29/17.
 */
public class TestUserFavoriteMediaController extends AbstractControllerTest {

    public static final String MEDIA_CONTROLLER_TEST_USER_NAME = "mediaControllerTestUser";

    @Autowired
    private UserMediaCatalogRepository userMediaCatalogRepository;


    @Test
    @WithMockUser(username = "getFavouritesUser", roles={"USER"})
    public void getUserFavoritesWithMovieAndTVShow() throws Exception {
        User testUser = createUserInDb("getFavouritesUser");

        MediaFullDetails fightClubMovie = MediaUtils.getMediaShortDetailsBy("tt0137523");
        MediaFullDetails friendsTVSeries = MediaUtils.getMediaShortDetailsBy("tt0108778");

        List<MediaShortDetails> favouriteMedia = Arrays.asList(fightClubMovie, friendsTVSeries);
        addToFavourites(testUser, favouriteMedia);

        mockMvc.perform(get("/api/user/favourites"))
                .andExpect(content().json(json(favouriteMedia), false))
                .andExpect(status().isOk());
    }

    private void addToFavourites(User user, List<MediaShortDetails> mediaList) throws Exception {
        for (MediaShortDetails media : mediaList) {
            addToFavourites(user, media);
        }
    }

    private void addToFavourites(User user, MediaShortDetails media) throws Exception {
        mockMvc.perform(post("/api/user/" + user.getUsername() + "/favourites/add")
                .contentType(MediaType.APPLICATION_JSON).content(json(media)))
                .andExpect(status().isOk());

        media.setFavourite(true);
    }
}
