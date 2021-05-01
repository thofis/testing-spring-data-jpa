package com.example.accessingdatajpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Set;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.STARTING;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;

@SpringBootApplication
public class AccessingDataJpaApplication {

  private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(AccessingDataJpaApplication.class);
  }

  @Bean
  public CommandLineRunner demo(CustomerRepository repository,
                                CustomerSearchRepository customerSearchRepository,
                                JdbcTemplate jdbcTemplate) {
    return (args) -> {
      // create customer_search view
      jdbcTemplate.execute("drop table if exists v_customer_search");
      jdbcTemplate.execute("create view v_customer_search\n" +
                           "as\n" +
                           "select a.id as address_id, c.id customer_id, first_name, last_name, street, city\n" +
                           "from customer c,\n" +
                           "     address a\n" +
                           "where c.id = a.customer_id;\n");
      // save a few customers
      repository.save(Customer.builder()
                              .firstName("Jack")
                              .lastName("Bauer")
                              .addresses(Set.of(Address.builder()
                                                       .street("Some Street 1")
                                                       .city("New York")
                                                       .build(),
                                                Address.builder()
                                                       .street("Some Street 17")
                                                       .city("Las Vegas")
                                                       .build()
                              ))
                              .build());
      repository.save(Customer.builder()
                              .firstName("Chloe")
                              .lastName("O'Brian")
                              .addresses(Set.of(Address.builder()
                                                       .street("Some Street 2")
                                                       .city("Washington")
                                                       .build()))
                              .build());
      repository.save(Customer.builder()
                              .firstName("Kim")
                              .lastName("Bauer")
                              .addresses(Set.of(Address.builder()
                                                       .street("Some Street 3")
                                                       .city("Los Angeles")
                                                       .build()))
                              .build());

      // perform queries on customer-search-view
      List<CustomerSearch> customers = customerSearchRepository.findAll();
      customers.forEach(c -> log.info("Customer-Search-Result: {}", c));

      // query by example
      CustomerSearch customerSearch = CustomerSearch
          .builder()
          .lastName("Bau")
          .firstName(null)
//          .city("Las Vegas")
          .build();
      List<CustomerSearch> resultsByExample = customerSearchRepository.findAll(Example.of(customerSearch, matchingAll().withStringMatcher(STARTING)));
      resultsByExample.forEach(c -> log.info("Results by example: {}", c));

    };
  }

}
