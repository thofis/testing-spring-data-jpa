package com.example.accessingdatajpa;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@ActiveProfiles("test")
public class DataTest {

	@Autowired
	CustomerSearchRepository customerSearchRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Test
	void count_entries() {
		final long count = customerRepository.count();
		assertThat(count).isEqualTo(3);
	}
}
