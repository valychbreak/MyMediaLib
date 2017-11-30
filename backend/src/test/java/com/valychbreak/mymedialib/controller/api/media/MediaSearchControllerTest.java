package com.valychbreak.mymedialib.controller.api.media;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.ControllerTest;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
@DatabaseSetup(value = "/data/db/common/TestUser.xml", type = DatabaseOperation.DELETE_ALL)
public class MediaSearchControllerTest extends ControllerTest {
    @Test
    @WithMockUser(username = "user", roles={"USER"})
    public void mediaSearch() throws Exception {

        mockMvc.perform(get("/api/media/search?q=batman"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(20)));
    }

    @Test
    @WithMockUser(username = "user", roles={"USER"})
    public void mediaSearchWithPage() throws Exception {

        mockMvc.perform(get("/api/media/search").param("q", "batman").param("p", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page", is(2)))
                .andExpect(jsonPath("$.items", hasSize(20)));
    }
}
