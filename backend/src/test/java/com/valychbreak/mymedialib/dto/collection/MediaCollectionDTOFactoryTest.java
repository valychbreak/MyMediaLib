package com.valychbreak.mymedialib.dto.collection;

import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.Media;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.services.media.MediaDetailsProvider;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static com.valychbreak.mymedialib.dto.collection.MediaCollectionDTOBuilder.aMediaCollectionDTOBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MediaCollectionDTOFactoryTest {

    @Mock
    private User user;

    @Mock
    private MediaDetailsProvider mediaDetailsProvider;

    private MediaCollectionDTOFactory mediaCollectionDTOFactory;

    @Before
    public void setUp() throws Exception {
        when(user.getRole()).thenReturn(new Role(Role.USER_ROLE_NAME));

        mediaCollectionDTOFactory = new MediaCollectionDTOFactory(mediaDetailsProvider);
    }

    @Test
    public void factoryCreatesWithCorrectNameAndId() throws Exception {
        UserMediaCollection userMediaCollection = new UserMediaCollection("root_catalog");
        userMediaCollection.setId(10300L);

        MediaCollectionDTO mediaCollectionDTO = mediaCollectionDTOFactory.createWithMedia(userMediaCollection, user);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(mediaCollectionDTO.getName()).isEqualTo("root_catalog");
        softAssertions.assertThat(mediaCollectionDTO.getId()).isEqualTo(10300L);
        softAssertions.assertAll();
    }

    @Test
    public void createByUserMediaCollection() throws Exception {
        when(mediaDetailsProvider.getDetails(any(Media.class))).thenReturn(new MediaFullDetailsImpl());
        UserMediaCollection userMediaCollection = createUserMediaCollectionWithMedia("root", user);

        MediaCollectionDTO mediaCollectionDTO = mediaCollectionDTOFactory.createWithMedia(userMediaCollection, user);

        MediaCollectionDTO expected = aMediaCollectionDTOBuilder()
                .withName("root")
                .withMediaList(Arrays.asList(new MediaFullDetailsImpl()))
                .withOwner(new UserDTO(user))
                .build();

        assertThat(mediaCollectionDTO).isEqualTo(expected);
    }

    private UserMediaCollection createUserMediaCollectionWithMedia(String name, User owner) {
        UserMedia userMedia = mock(UserMedia.class);
        when(userMedia.getMedia()).thenReturn(mock(Media.class));

        UserMediaCollection userMediaCollection = new UserMediaCollection(name, owner);
        userMediaCollection.getUserMediaList().add(userMedia);
        return userMediaCollection;
    }

}