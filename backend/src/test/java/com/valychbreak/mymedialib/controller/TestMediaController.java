package com.valychbreak.mymedialib.controller;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.valychbreak.mymedialib.data.MediaFullDetails;
import com.valychbreak.mymedialib.data.MediaShortDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.UserMedia;
import com.valychbreak.mymedialib.services.OmdbVideoProvider;
import com.valychbreak.mymedialib.tools.adapters.MediaFullDetailsAdapter;
import com.valychbreak.mymedialib.tools.adapters.MediaShortDetailsAdapter;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static com.valychbreak.mymedialib.controller.TestMediaController.MEDIA_CONTROLLER_TEST_USER_NAME;

/**
 * Created by valych on 4/29/17.
 */
@WithMockUser(username = MEDIA_CONTROLLER_TEST_USER_NAME, roles={"USER"})
public class TestMediaController extends AbstractControllerTest {

    public static final String MEDIA_CONTROLLER_TEST_USER_NAME = "mediaControllerTestUser";

    private User testUser;

    @Test
    public void testGetUserFavourites() throws Exception {
        MediaFullDetails fightClubMovie = getMediaShortDetailsBy("tt0137523");
        MediaFullDetails friendsTVSeries = getMediaShortDetailsBy("tt0108778");

        List<MediaShortDetails> favouriteMedia = Arrays.asList(fightClubMovie, friendsTVSeries);
        addToFavourites(testUser, favouriteMedia);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/favourites"))
                .andExpect(MockMvcResultMatchers.content().json(json(favouriteMedia), false))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddUserFavourites() throws Exception {
        MediaFullDetails fightClubMovie = getMediaShortDetailsBy("tt0137523");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/" + testUser.getUsername() + "/favourites/add")
                .contentType(MediaType.APPLICATION_JSON).content(json(fightClubMovie)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        User dbTestUser = userRepository.findFirstByUsername(MEDIA_CONTROLLER_TEST_USER_NAME);
        boolean found = userHasInFavourites(fightClubMovie, dbTestUser);

        Assert.assertTrue(fightClubMovie.getTitle() + " media is not in " + dbTestUser.getUsername() + "'s favourites", found);
    }

    private boolean userHasInFavourites(MediaFullDetails fightClubMovie, User dbTestUser) {
        boolean found = false;
        for (UserMedia userMedia : dbTestUser.getFavourites()) {
            if(userMedia.getMedia().getImdbId().equals(fightClubMovie.getImdbId())) {
                found = true;
            }
        }
        return found;
    }

    @Override
    protected void setupTest() throws Exception {
        super.setupTest();

        testUser = createUserInDb(MEDIA_CONTROLLER_TEST_USER_NAME);
    }

    private void initFavourites(User user) throws Exception {
    }

    private MediaFullDetails getMediaShortDetailsBy(String imdbId) throws OMDBException {
        OmdbVideoProvider provider = new OmdbVideoProvider();
        OmdbVideoFull movie = provider.getOmdbVideo(imdbId);
        return new MediaFullDetailsAdapter(movie);
    }

    private void addToFavourites(User user, List<MediaShortDetails> mediaList) throws Exception {
        for (MediaShortDetails media : mediaList) {
            addToFavourites(user, media);
        }
    }

    private void addToFavourites(User user, MediaShortDetails media) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/" + user.getUsername() + "/favourites/add")
                .contentType(MediaType.APPLICATION_JSON).content(json(media)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        media.setFavourite(true);
    }
}
