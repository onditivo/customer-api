package com.esg.exercise.customer.controller;

import com.esg.exercise.customer.error.ItemNotFoundException;
import com.esg.exercise.customer.model.Customer;
import com.esg.exercise.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Optional;

/**
 * An API to manage customer information
 */
@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Create a customer in the persistence store.
     * Validate the input before proceeding.
     * Return the persisted customer information.
     * @param customer
     * @return
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid Customer customer) {
        try {
            Customer customerData = customerService.saveOrUpdateCustomer(customer);

            return new ResponseEntity<>(customerData, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve a customer information using a customer's reference number.
     * If no customer found, return an error indicating so.
     * @param reference
     * @return
     */
    @GetMapping(value = "/{reference}",  produces = "application/json")
    public ResponseEntity<Customer> findByCustomerRef(final @PathVariable("reference") String reference) {
        Optional<Customer> customerData = customerService.findCustomer(reference);

        return customerData.map(customer -> new ResponseEntity<>(customerData.get(), HttpStatus.OK))
                .orElseThrow(() -> new ItemNotFoundException(reference));
                //.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
