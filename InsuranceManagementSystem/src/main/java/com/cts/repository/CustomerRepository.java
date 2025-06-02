package com.cts.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entity.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContainingIgnoreCase(String name);
}