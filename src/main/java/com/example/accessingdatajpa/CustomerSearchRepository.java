package com.example.accessingdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSearchRepository extends JpaRepository<CustomerSearch, Long> {
}
