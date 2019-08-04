package com.valychbreak.mymedialib.controller.api.user;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.AbstractControllerTest;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.testtools.MediaUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup(value = "/data/db/BasicRolesDataset.xml")
@DatabaseSetup(value = "/data/db/UserFavoriteMediaControllerTest.xml")
public class UserFavoriteMediaControllerTest extends AbstractControllerTest {

    @Autowired
    private MediaUtils mediaUtils;

    @Test
    @WithMockUser(username = "test_user", roles={"USER"})
    public void getUserFavoritesWithMovieAndTVShow() throws Exception {
        User testUser = userRepository.findFirstByUsername("test_user");

        MediaFullDetails fightClubMovie = mediaUtils.getMediaFullDetailsBy("tt0137523");
        MediaFullDetails friendsTVSeries = mediaUtils.getMediaFullDetailsBy("tt0108778");

        List<MediaShortDetails> favouriteMedia = Arrays.asList(fightClubMovie, friendsTVSeries);
        addToFavourites(testUser, favouriteMedia);

        mockMvc.perform(get("/api/user/favourites"))
                .andExpect(content().json(json(favouriteMedia), false))
                .andExpect(status().isOk());
    }

    // TODO: usernames should be different for mocked and concrete user. Add favorites in dbunit data set
    @Test
    @WithMockUser(username = "another_user", roles={"USER"})
    public void userCanSeeAnotherUserFavoritesMoviesAndTVShows() throws Exception {
        User userWithFavorites = userRepository.findFirstByUsername("another_user");

        MediaFullDetails fightClubMovie = mediaUtils.getMediaFullDetailsBy("tt0137523");
        MediaFullDetails friendsTVSeries = mediaUtils.getMediaFullDetailsBy("tt0108778");

        List<MediaShortDetails> favouriteMedia = Arrays.asList(fightClubMovie, friendsTVSeries);
        addToFavourites(userWithFavorites, favouriteMedia);

        mockMvc.perform(get("/api/user/" + userWithFavorites.getUsername() + "/favourites"))
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
