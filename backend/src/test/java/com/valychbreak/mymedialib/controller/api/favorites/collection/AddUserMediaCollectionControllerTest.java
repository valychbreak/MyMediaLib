package com.valychbreak.mymedialib.controller.api.favorites.collection;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.google.common.collect.Lists;
import com.valychbreak.mymedialib.controller.ControllerTest;
import com.valychbreak.mymedialib.dto.UserDTO;
import com.valychbreak.mymedialib.dto.collection.MediaCollectionDTO;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.valychbreak.mymedialib.dto.collection.MediaCollectionDTOBuilder.aMediaCollectionDTOBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestExecutionListeners({WithSecurityContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
public class AddUserMediaCollectionControllerTest extends ControllerTest {

    private UserMediaCollectionRepository userMediaCollectionRepository;

    @Autowired
    private UserRepository userRepository;

    // TODO: fails when all tests are run, but passes when run alone
    @Test
    @DatabaseSetup("/data/db/UserMediaCatalogsForMediaCatalogControllerTest.xml")
    @Transactional
    public void addMediaCollection() throws Exception {
        User user = userRepository.findFirstByUsername("test");
        UserDTO userDTO = new UserDTO(user);

        //userMediaCollectionRepository.findAll()
        MediaCollectionDTO expectedMediaCollection = aMediaCollectionDTOBuilder()
                .withId(1L)
                .withName("New Catalog Name")
                .withMediaList(Lists.newArrayList())
                .withOwner(userDTO)
                .build();

        MediaCollectionDTO newMediaCollection = aMediaCollectionDTOBuilder().withName("New Catalog Name").build();
        mockMvc.perform(
                post("/api/collection/add").contentType(MediaType.APPLICATION_JSON).content(json(newMediaCollection))
                        .principal(new TestingAuthenticationToken("test", "test12")))
                .andExpect(status().isOk())
                .andExpect(content().json(json(expectedMediaCollection)));
    }

}