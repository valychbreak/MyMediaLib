package com.valychbreak.mymedialib.controller.api.favorites.collection;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.ControllerTest;
import com.valychbreak.mymedialib.dto.collection.MediaCollectionDTO;
import com.valychbreak.mymedialib.dto.collection.MediaCollectionDTOFactory;
import com.valychbreak.mymedialib.entity.User;
import com.valychbreak.mymedialib.entity.media.UserMediaCollection;
import com.valychbreak.mymedialib.repository.UserMediaCollectionRepository;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestExecutionListeners({WithSecurityContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
public class UserMediaCollectionControllerTest extends ControllerTest {
    @Autowired
    private UserMediaCollectionRepository userMediaCollectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DatabaseSetup("/data/db/UserMediaCatalogsForMediaCatalogControllerTest.xml")
    @Transactional
    public void getCollectionWithoutMedia() throws Exception {
        UserMediaCollection userMediaCollection = userMediaCollectionRepository.findOne(1000L);

        MediaCollectionDTO expectedMediaCollectionDTO = new MediaCollectionDTOFactory().createWithoutMedia(userMediaCollection);
        mockMvc.perform(get("/api/collection/1000").requestAttr("media", "false"))
                .andExpect(status().isOk())
                .andExpect(content().json(json(expectedMediaCollectionDTO)));
    }

    @Test
    @DatabaseSetup("/data/db/UserMediaCatalogsForMediaCatalogControllerTest.xml")
    @Transactional
    public void getCollectionWithMedia() throws Exception {
        UserMediaCollection userMediaCollection = userMediaCollectionRepository.findOne(1000L);

        MediaCollectionDTO expectedMediaCollectionDTO = new MediaCollectionDTOFactory().createWithMedia(userMediaCollection);
        mockMvc.perform(get("/api/collection/1000").requestAttr("media", "true"))
                .andExpect(status().isOk())
                .andExpect(content().json(json(expectedMediaCollectionDTO)));
    }

    @Test
    @WithMockUser(username = "test", roles={"USER"})
    @DatabaseSetup("/data/db/UserMediaCatalogsForMediaCatalogControllerTest.xml")
    @Transactional
    public void getUserRootCollectionWithoutMedia() throws Exception {
        User user = userRepository.findOne(1000L);

        MediaCollectionDTO expectedMediaCollectionDTO = new MediaCollectionDTOFactory().createWithoutMedia(user.getRootUserMediaCollection());
        mockMvc.perform(get("/api/user/collection/root").requestAttr("media", "false"))
                .andExpect(status().isOk())
                .andExpect(content().json(json(expectedMediaCollectionDTO)));
    }

    @Test
    @DatabaseSetup("/data/db/UserMediaCatalogsForMediaCatalogControllerTest.xml")
    @WithMockUser(username = "test", roles={"USER"})
    @Transactional
    public void getUserRootCollectionWithMedia() throws Exception {
        User user = userRepository.findOne(1000L);

        MediaCollectionDTO expectedMediaCollectionDTO = new MediaCollectionDTOFactory().createWithoutMedia(user.getRootUserMediaCollection());
        mockMvc.perform(get("/api/user/collection/root").requestAttr("media", "false"))
                .andExpect(status().isOk())
                .andExpect(content().json(json(expectedMediaCollectionDTO)));
    }
}