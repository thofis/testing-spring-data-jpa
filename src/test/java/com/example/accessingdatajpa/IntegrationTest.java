package com.example.accessingdatajpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(controllers = HelloController.class)
@ActiveProfiles(profiles = {"test"})
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
           .andExpect(content().string(containsString("world")));
  }

  @Test
  void test_hello_custom_name() throws Exception {
    mockMvc.perform(get("/hello?name=thomas"))
           .andExpect(status().is2xxSuccessful())
           .andExpect(content().string(containsString("thomas")));
  }

  @Test
  void test_not_found() throws Exception {
    mockMvc.perform(get("/helloX"))
           .andExpect(status().is4xxClientError());
  }

  @Test
  void test_exception() throws Exception {
    mockMvc.perform(get("/dummy-exception"))
           .andExpect(status().is5xxServerError());
  }

}
