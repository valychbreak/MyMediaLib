package com.valychbreak.mymedialib.controller.api.favorites.catalogs;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.google.common.collect.Lists;
import com.valychbreak.mymedialib.controller.ControllerTest;
import com.valychbreak.mymedialib.dto.catalog.MediaCatalogDTO;
import com.valychbreak.mymedialib.entity.media.UserMediaCatalog;
import com.valychbreak.mymedialib.repository.UserMediaCatalogRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import static com.valychbreak.mymedialib.dto.catalog.MediaCatalogDTOBuilder.aMediaCatalogDTOBuilder;
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

    // TODO: fails when all tests are run, but passes when run alone
    @Test
    @DatabaseSetup("/data/db/UserMediaCatalogsForMediaCatalogControllerTest.xml")
    @Transactional
    public void addMediaCatalog() throws Exception {
        UserMediaCatalog parentUserMediaCatalog = userMediaCatalogRepository.findOne(1000L);

        MediaCatalogDTO parentMediaCatalogDTO = aMediaCatalogDTOBuilder().withId(parentUserMediaCatalog.getId()).withName(parentUserMediaCatalog.getName()).build();
        MediaCatalogDTO expectedMediaCatalog = aMediaCatalogDTOBuilder().withId(1L).withName("New Catalog Name").withParent(parentMediaCatalogDTO).withMediaList(Lists.newArrayList()).withSubCatalogs(Lists.newArrayList()).build();

        MediaCatalogDTO newMediaCatalog = aMediaCatalogDTOBuilder().withName("New Catalog Name").withParent(parentMediaCatalogDTO).build();
        mockMvc.perform(post("/api/catalog/add").contentType(MediaType.APPLICATION_JSON).content(json(newMediaCatalog)))
                .andExpect(status().isOk())
                .andExpect(content().json(json(expectedMediaCatalog)));
    }

}