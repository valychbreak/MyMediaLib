package com.valychbreak.mymedialib.controller.api.favorites.collection;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.ControllerTest;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMedia;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import com.valychbreak.mymedialib.repository.UserMediaRepository;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
public class RemoveUserMediaCollectionControllerTest extends ControllerTest {

    @Autowired
    private UserMediaCollectionRepository userMediaCollectionRepository;

    @Autowired
    private UserMediaRepository userMediaRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup(value = "/data/db/RemoveUserMediaCollectionControllerTest.xml")
    public void removeCollection() throws Exception {
        mockMvc.perform(post("/api/collection/1000/remove"))
                .andExpect(status().isOk());

        UserMediaCollection removedCollection = userMediaCollectionRepository.findById(1000L).orElse(null);
        assertThat(removedCollection).isNull();

        User user = userRepository.findFirstByUsername("test");
        List<UserMedia> favoriteMedia = userMediaRepository.findByUser(user);
        assertThat(favoriteMedia).hasSize(1);
    }
}