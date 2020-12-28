package org.gitanjali.exam;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;


@WebMvcTest
class ExamApplicationTests {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    TestRepository testRepository;

	@Test
	void contextLoads() throws Exception {


	    Mockito.when(testRepository.findAll()).thenReturn(
                Collections.emptyList()
        );

	    MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/tests/all")
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn();

        System.out.println(mvcResult.getResponse());
	}

}
