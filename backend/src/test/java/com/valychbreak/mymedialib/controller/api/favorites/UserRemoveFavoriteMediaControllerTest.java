package com.valychbreak.mymedialib.controller.api.favorites;

import com.valychbreak.mymedialib.controller.AbstractControllerTest;
import com.valychbreak.mymedialib.data.movie.MediaFullDetails;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import com.valychbreak.mymedialib.testtools.MediaUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRemoveFavoriteMediaControllerTest extends AbstractControllerTest {
    @Autowired
    private UserMediaRepository userMediaRepository;

    @Autowired
    private MediaUtils mediaUtils;

    @Test
    @WithMockUser(username = "removeFavouritesUser", roles = "USER")
    public void removeFavourite() throws Exception {
        User testUser = createUserInDb("removeFavouritesUser");

        MediaFullDetails spiderManMovie = mediaUtils.getMediaFullDetailsBy("tt0145487");
        MediaFullDetails friendsTVSeries = mediaUtils.getMediaFullDetailsBy("tt0108778");

        List<MediaShortDetails> favouriteMedia = Arrays.asList(spiderManMovie, friendsTVSeries);
        addToFavourites(testUser, favouriteMedia);

        mockMvc.perform(post("/api/user/favourites/remove").contentType(MediaType.APPLICATION_JSON).content(json(spiderManMovie)))
                .andExpect(status().isOk());

        User dbTestUser = userRepository.findFirstByUsername(testUser.getUsername());

        assertThat(isUserFavorite(dbTestUser, spiderManMovie)).isFalse();
    }

    private boolean isUserFavorite(User dbTestUser, MediaFullDetails fightClubMovie) {
        boolean found = false;
        for (UserMedia userMedia : userMediaRepository.findByUser(dbTestUser)) {
            if(userMedia.getMedia().getImdbId().equals(fightClubMovie.getImdbId())) {
                found = true;
            }
        }
        return found;
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