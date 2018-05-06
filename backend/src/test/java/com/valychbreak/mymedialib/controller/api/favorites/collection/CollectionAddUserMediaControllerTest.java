package com.valychbreak.mymedialib.controller.api.favorites.collection;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.ControllerTest;
import com.valychbreak.mymedialib.data.movie.MediaShortDetails;
import com.valychbreak.mymedialib.data.movie.impl.MediaShortDetailsImpl;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestExecutionListeners({WithSecurityContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
public class CollectionAddUserMediaControllerTest extends ControllerTest {

    @Autowired
    private UserMediaCollectionRepository userMediaCollectionRepository;

    @Autowired
    private UserMediaRepository userMediaRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup(value = "/data/db/CollectionAddUserMediaControllerTest.xml")
    @Transactional
    public void addMediaToCollection() throws Exception {
        MediaShortDetails mediaShortDetails = new MediaShortDetailsImpl("tt112233", "Fight club");
        mockMvc.perform(
                post("/api/collection/1000/add-media")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json(mediaShortDetails))
                        .principal(new TestingAuthenticationToken("test", "test12")))
                .andExpect(status().isOk());

        User user = userRepository.findFirstByUsername("test");

        UserMediaCollection userMediaCollection = userMediaCollectionRepository.findOne(1000L);
        assertThat(userMediaCollection.getUserMediaList()).hasSize(1);

        List<UserMedia> userFavorites = userMediaRepository.findByUser(user);
        assertThat(userFavorites).hasSize(1);
    }

    @Test
    @DatabaseSetup(value = "/data/db/CollectionAddUserMediaControllerWithMediaInFavoritesTest.xml")
    @Transactional
    public void addMediaToCollectionWithMediaAlreadyInFavorites() throws Exception {
        MediaShortDetails mediaShortDetails = new MediaShortDetailsImpl("tt999999", "New Super-Cool Movie");
        mockMvc.perform(
                post("/api/collection/1000/add-media")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(json(mediaShortDetails))
                        .principal(new TestingAuthenticationToken("test", "test12")))
                .andExpect(status().isOk());

        User user = userRepository.findFirstByUsername("test");

        UserMediaCollection userMediaCollection = userMediaCollectionRepository.findOne(1000L);
        assertThat(userMediaCollection.getUserMediaList()).hasSize(1);

        List<UserMedia> userFavorites = userMediaRepository.findByUser(user);
        assertThat(userFavorites).hasSize(1);
    }
}