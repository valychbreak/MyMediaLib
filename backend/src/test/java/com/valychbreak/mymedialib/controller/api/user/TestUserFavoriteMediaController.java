package com.valychbreak.mymedialib.controller.api.user;

import com.valychbreak.mymedialib.controller.AbstractControllerTest;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import com.valychbreak.mymedialib.testtools.MediaUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

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
    private UserMediaCollectionRepository userMediaCollectionRepository;


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

    // TODO: usernames should be different for mocked and concrete user. Should be fixed when xml datasets are used
    @Test
    @WithMockUser(username = "getFavouritesOfConcreteUser", roles={"USER"})
    public void getParticularUserFavoritesWithMovieAndTVShow() throws Exception {
        User testUser = createUserInDb("getFavouritesOfConcreteUser");

        MediaFullDetails fightClubMovie = MediaUtils.getMediaShortDetailsBy("tt0137523");
        MediaFullDetails friendsTVSeries = MediaUtils.getMediaShortDetailsBy("tt0108778");

        List<MediaShortDetails> favouriteMedia = Arrays.asList(fightClubMovie, friendsTVSeries);
        addToFavourites(testUser, favouriteMedia);

        mockMvc.perform(get("/api/user/" + testUser.getUsername() + "/favourites"))
                .andExpect(content().json(json(favouriteMedia), false))
                .andExpect(status().isOk());
    }

    private void addToFavourites(User user, List<MediaShortDetails> mediaList) throws Exception {
        for (MediaShortDetails media : mediaList) {
            addToFavourites(user, media);
        }
    }

    private void addToFavourites(User user, MediaShortDetails media) throws Exception {
        mockMvc.perform(post("/api/user/favourites/add")
                .contentType(MediaType.APPLICATION_JSON).content(json(media)))
                .andExpect(status().isOk());

        media.setFavourite(true);
    }
}
