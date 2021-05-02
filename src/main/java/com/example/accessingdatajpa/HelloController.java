package com.example.accessingdatajpa;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {
	@GetMapping(value = "/hello", produces = "application/json")
	public HelloModel hello(@RequestParam(name = "name", defaultValue = "${tests.name}") final String name) {
		log.warn("Hello");

		// @formatter:off
		return HelloModel.builder()
				.firstName("Someone")
				.lastName(name)
				.build();
		// @formatter:on
	}

	@GetMapping("/dummy-exception")
	public void throwException() {
		log.error("throwing an exception");
		throw new RuntimeException("dummy");
	}
}
