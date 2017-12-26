package com.valychbreak.mymedialib.controller.api.favorites.catalogs;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.ControllerTest;
import com.valychbreak.mymedialib.dto.catalog.MediaCatalogDTO;
import com.valychbreak.mymedialib.dto.catalog.MediaCatalogDTOFactory;
import com.valychbreak.mymedialib.entity.media.UserMediaCatalog;
import com.valychbreak.mymedialib.repository.UserMediaCatalogRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestExecutionListeners({WithSecurityContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
public class AddUserMediaCatalogControllerTest extends ControllerTest {

    @Autowired
    private UserMediaCatalogRepository userMediaCatalogRepository;

    @Test
    @DatabaseSetup("/data/db/UserMediaCatalogsForMediaCatalogControllerTest.xml")
    @Transactional
    public void addMediaCatalog() throws Exception {
        UserMediaCatalog userMediaCatalog = userMediaCatalogRepository.findOne(1000L);

        MediaCatalogDTO parentMediaCatalogDTO = new MediaCatalogDTO(userMediaCatalog.getId(), userMediaCatalog.getName(), null, null, null);
        MediaCatalogDTO expectedMediaCatalog = new MediaCatalogDTO(1L, "New Catalog Name", parentMediaCatalogDTO, new ArrayList<>(), new ArrayList<>());

        mockMvc.perform(post("/api/catalog/1000/add").param("name", "New Catalog Name"))
                .andExpect(status().isOk())
                .andExpect(content().json(json(expectedMediaCatalog)));
    }

}