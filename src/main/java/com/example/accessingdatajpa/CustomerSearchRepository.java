package com.example.accessingdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerSearchRepository
    extends JpaRepository<CustomerSearch, Long>, JpaSpecificationExecutor<CustomerSearch>
{
}
