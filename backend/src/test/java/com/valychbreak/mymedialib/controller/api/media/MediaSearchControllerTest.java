package com.valychbreak.mymedialib.controller.api.media;

import com.valychbreak.mymedialib.controller.AbstractControllerTest;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by valych on 9/16/17.
 */
public class MediaSearchControllerTest extends AbstractControllerTest {
    @Test
    @WithMockUser(username = "user", roles={"USER"})
    public void mediaSearch() throws Exception {

        mockMvc.perform(get("/api/media/search?q=batman"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(20)));
    }
}
