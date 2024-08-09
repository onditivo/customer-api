package com.esg.exercise.customer.repository;

import com.esg.exercise.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Use default repository methods for CRUD operation and add definition for non-supported method
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Find a customer given a customer reference
    Optional<Customer> findByCustomerRef(String customerRef);
}
