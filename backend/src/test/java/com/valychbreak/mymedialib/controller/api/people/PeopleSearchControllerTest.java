package com.valychbreak.mymedialib.controller.api.people;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.valychbreak.mymedialib.controller.ControllerTest;
import com.valychbreak.mymedialib.dto.person.BasicPersonDTO;
import com.valychbreak.mymedialib.services.utils.SearchResult;
import org.junit.Test;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/data/db/common/CleanDb.xml", type = DatabaseOperation.DELETE_ALL)
public class PeopleSearchControllerTest extends ControllerTest {
    @Test
    public void searchPeople() throws Exception {
        BasicPersonDTO expectedBasicPerson = new BasicPersonDTO(287L, "Brad Pitt", new ArrayList<>());
        SearchResult<BasicPersonDTO> expected = new SearchResult<>(1, 1, Arrays.asList(expectedBasicPerson));
        String expectedContent = json(expected);
        mockMvc.perform(get("/api/people/search").requestAttr("q", "Brad Pitt").requestAttr("p", 1))
                .andExpect(status().isOk()).andExpect(content().json(expectedContent));
    }

}