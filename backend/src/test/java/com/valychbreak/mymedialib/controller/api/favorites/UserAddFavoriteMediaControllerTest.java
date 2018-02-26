package com.valychbreak.mymedialib.controller.api.favorites;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.valychbreak.mymedialib.controller.AbstractControllerTest;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.adapters.MediaFullDetailsAdapter;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import com.valychbreak.mymedialib.services.OmdbVideoProvider;
import com.valychbreak.mymedialib.testtools.MediaUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAddFavoriteMediaControllerTest extends AbstractControllerTest {
    @Autowired
    private UserMediaCollectionRepository userMediaCollectionRepository;


    @Test
    @WithMockUser(username = "testUserAddFavorites", roles={"USER"})
    @Transactional
    public void addFavoriteToLoggedUser() throws Exception {
        User testUser = createUserInDb("testUserAddFavorites");

        MediaFullDetails fightClubMovie = MediaUtils.getMediaShortDetailsBy("tt0137523");
        mockMvc.perform(post("/api/user/favourites/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(fightClubMovie)))
                .andExpect(status().isOk());

        User dbTestUser = userRepository.findFirstByUsername(testUser.getUsername());

        assertThat(isUserFavorite(dbTestUser, fightClubMovie)).isTrue();
    }

    @Test
    @WithMockUser(username = "testAddUser", roles={"USER"})
    @Transactional
    public void testAddUserFavouritesToSubCategory() throws Exception {
        User testUser = createUserInDb("testAddUser");

        UserMediaCollection rootUserMediaCollection = testUser.getRootUserMediaCollection();
        UserMediaCollection userMediaCollection = new UserMediaCollection("testCat");
        userMediaCollection.setParentUserMediaCollection(rootUserMediaCollection);
        userMediaCollectionRepository.save(userMediaCollection);

        rootUserMediaCollection.getSubUserMediaCollections().add(userMediaCollection);

        userMediaCollectionRepository.save(rootUserMediaCollection);


        MediaFullDetails fightClubMovie = MediaUtils.getMediaShortDetailsBy("tt0137523");
        mockMvc.perform(post("/api/user/favourites/" + userMediaCollection.getId() + "/add")
                .contentType(MediaType.APPLICATION_JSON).content(json(fightClubMovie)))
                .andExpect(status().isOk());

        User dbTestUser = userRepository.findFirstByUsername(testUser.getUsername());

        assertThat(isUserFavorite(dbTestUser, fightClubMovie)).isTrue();
    }

    private boolean isUserFavorite(User dbTestUser, MediaFullDetails fightClubMovie) {
        UserMediaCollection userMediaCollection = dbTestUser.getRootUserMediaCollection();
        boolean found = isInCollectionOrSubCollection(fightClubMovie, userMediaCollection);
        return found;
    }

    private boolean isInCollectionOrSubCollection(MediaFullDetails fightClubMovie, UserMediaCollection userMediaCollection) {
        boolean found = false;
        for (UserMedia userMedia : userMediaCollection.getUserMediaList()) {
            if(userMedia.getMedia().getImdbId().equals(fightClubMovie.getImdbId())) {
                found = true;
            }
        }

        if(!found) {
            for (UserMediaCollection mediaCollection : userMediaCollection.getSubUserMediaCollections()) {
                if (!found) {
                    found = isInCollectionOrSubCollection(fightClubMovie, mediaCollection);
                }
            }
        }

        return found;
    }
    private MediaFullDetails getMediaShortDetailsBy(String imdbId) throws OMDBException {
        OmdbVideoProvider provider = new OmdbVideoProvider();
        OmdbVideoFull movie = provider.getOmdbVideo(imdbId);
        return new MediaFullDetailsAdapter(movie);
    }

    private void addToFavourites(User user, MediaShortDetails media) throws Exception {
        mockMvc.perform(post("/api/user/" + user.getUsername() + "/favourites/add")
                .contentType(MediaType.APPLICATION_JSON).content(json(media)))
                .andExpect(status().isOk());

        media.setFavourite(true);
    }

}