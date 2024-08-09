package com.esg.exercise.customer.service;

import com.esg.exercise.customer.model.Customer;
import com.esg.exercise.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Customer information service. A layer between persistence store and the controller layer
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    public Optional<Customer> findCustomer(String customerReference) {
        return repository.findByCustomerRef(customerReference);
    }

    @Transactional
    public Customer saveOrUpdateCustomer(Customer customer) {
        // Persisting a new customer or merging changes for an existing customer
        return repository.save(customer);
    }
}
