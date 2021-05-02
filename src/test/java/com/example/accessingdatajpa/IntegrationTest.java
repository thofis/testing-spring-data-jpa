package com.example.accessingdatajpa;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(controllers = HelloController.class)
@ActiveProfiles(profiles = { "test" })
class IntegrationTest {


	@Autowired
	MockMvc mockMvc;

	@MockBean
	CustomerRepository customerRepository;

	@MockBean
	CustomerSearchRepository customerSearchRepository;

	@MockBean
	JdbcTemplate jdbcTemplate;

	@Test
	void test_hello_world() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string(containsString("World")));
	}

	@Test
	void test_hello_custom_name_json() throws Exception {
		mockMvc.perform(get("/hello?name=thomas"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().json("{\n"
						+ "  \"firstName\":"
						+ "  \"Someone\",\n"
						+ "  \"lastName\": \"thomas\"\n"
						+ "}"));
	}

	@Test
	void test_hello_custom_name_jsonfile() throws Exception {
		mockMvc.perform(get("/hello?name=thomas"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().json(getJsonFromFile("expected.json")));
	}

	private String getJsonFromFile(String filename) throws IOException {
		Resource expectedResult = new ClassPathResource(filename);
		final String expectedJson = Files.readString(expectedResult.getFile().toPath());
		return expectedJson;
	}

	@Test
	void test_hello_custom_name_jsonpath() throws Exception {
		mockMvc.perform(get("/hello?name=thomas"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.firstName").value("Someone"))
				.andExpect(jsonPath("$.lastName").value("thomas"));
	}

	@Test
	void test_not_found() throws Exception {
		mockMvc.perform(get("/helloX"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void test_exception() throws Exception {
		Assertions.assertThrows(NestedServletException.class, () -> {
			mockMvc.perform(get("/dummy-exception"))
					.andExpect(status().is5xxServerError());
		});
	}

}
