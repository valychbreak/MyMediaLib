package com.valychbreak.mymedialib.controller;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCatalog;
import com.valychbreak.mymedialib.repository.UserMediaCatalogRepository;
import com.valychbreak.mymedialib.services.OmdbVideoProvider;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsAdapter;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Created by valych on 4/29/17.
 */
public class TestUserMediaController extends AbstractControllerTest {

    public static final String MEDIA_CONTROLLER_TEST_USER_NAME = "mediaControllerTestUser";

    //private User testUser;
    @Autowired
    private UserMediaCatalogRepository userMediaCatalogRepository;

    @Test
    @WithMockUser(username = "getFavouritesUser", roles={"USER"})
    public void testGetUserFavourites() throws Exception {
        User testUser = createUserInDb("getFavouritesUser");

        MediaFullDetails fightClubMovie = getMediaShortDetailsBy("tt0137523");
        MediaFullDetails friendsTVSeries = getMediaShortDetailsBy("tt0108778");

        List<MediaShortDetails> favouriteMedia = Arrays.asList(fightClubMovie, friendsTVSeries);
        addToFavourites(testUser, favouriteMedia);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/favourites"))
                .andExpect(MockMvcResultMatchers.content().json(json(favouriteMedia), false))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "testAddUser", roles={"USER"})
    @Transactional
    public void testAddUserFavouritesToRoot() throws Exception {
        User testUser = createUserInDb("testAddUser");

        MediaFullDetails fightClubMovie = getMediaShortDetailsBy("tt0137523");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/" + testUser.getUsername() + "/favourites/add")
                .contentType(MediaType.APPLICATION_JSON).content(json(fightClubMovie)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        User dbTestUser = userRepository.findFirstByUsername(testUser.getUsername());
        boolean found = userHasInFavourites(fightClubMovie, dbTestUser);

        Assert.assertTrue(fightClubMovie.getTitle() + " media is not in " + dbTestUser.getUsername() + "'s favourites", found);
    }

    @Test
    @WithMockUser(username = "testAddUser", roles={"USER"})
    @Transactional
    public void testAddUserFavouritesToSubCategory() throws Exception {
        User testUser = createUserInDb("testAddUser");

        UserMediaCatalog rootUserMediaCatalog = testUser.getRootUserMediaCatalog();
        UserMediaCatalog userMediaCatalog = new UserMediaCatalog("testCat");
        userMediaCatalog.setParentUserMediaCatalog(rootUserMediaCatalog);
        userMediaCatalogRepository.save(userMediaCatalog);

        rootUserMediaCatalog.getSubUserMediaCatalogs().add(userMediaCatalog);

        userMediaCatalogRepository.save(rootUserMediaCatalog);


        MediaFullDetails fightClubMovie = getMediaShortDetailsBy("tt0137523");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/" + testUser.getUsername() + "/favourites/" + userMediaCatalog.getId() + "/add")
                .contentType(MediaType.APPLICATION_JSON).content(json(fightClubMovie)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        User dbTestUser = userRepository.findFirstByUsername(testUser.getUsername());
        boolean found = userHasInFavourites(fightClubMovie, dbTestUser);

        Assert.assertTrue(fightClubMovie.getTitle() + " media is not in " + dbTestUser.getUsername() + "'s favourites", found);
    }

    private boolean userHasInFavourites(MediaFullDetails fightClubMovie, User dbTestUser) {
        UserMediaCatalog userMediaCatalog = dbTestUser.getRootUserMediaCatalog();
        boolean found = isInCatalogOrSubCatalog(fightClubMovie, userMediaCatalog);
        return found;
    }

    private boolean isInCatalogOrSubCatalog(MediaFullDetails fightClubMovie, UserMediaCatalog userMediaCatalog) {
        boolean found = false;
        for (UserMedia userMedia : userMediaCatalog.getUserMediaList()) {
            if(userMedia.getMedia().getImdbId().equals(fightClubMovie.getImdbId())) {
                found = true;
            }
        }

        if(!found) {
            for (UserMediaCatalog mediaCatalog : userMediaCatalog.getSubUserMediaCatalogs()) {
                if (!found) {
                    found = isInCatalogOrSubCatalog(fightClubMovie, mediaCatalog);
                }
            }
        }

        return found;
    }

    @Override
    protected void setupTest() throws Exception {
        super.setupTest();

        //testUser = createUserInDb(MEDIA_CONTROLLER_TEST_USER_NAME);
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
