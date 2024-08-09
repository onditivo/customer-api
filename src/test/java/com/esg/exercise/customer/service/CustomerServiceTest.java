package com.esg.exercise.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.esg.exercise.customer.model.Customer;

@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void saveCustomerToDatabase() {
        Customer customer = crateCustomer("cust1000");

        Customer customerData = customerService.saveOrUpdateCustomer(customer);

        assertEquals(customer.getCustomerRef(), customerData.getCustomerRef());
    }

    @Test
    public void findSavedCustomerByReference() {
        Customer customer = crateCustomer("cust2000");

        customerService.saveOrUpdateCustomer(customer);

        Optional<Customer> customerData = customerService.findCustomer("cust2000");

        assertTrue(customerData.isPresent());
        assertEquals(customer.getCustomerRef(), customerData.get().getCustomerRef());
    }

    @Test
    public void findUnsavedCustomerByReference() {
        Customer customer = crateCustomer("cust3000");

        customerService.saveOrUpdateCustomer(customer);

        Optional<Customer> customerData = customerService.findCustomer("cust4000");

        assertTrue(customerData.isEmpty());
    }

    private Customer crateCustomer(String reference) {
        Customer c1 = new Customer();
        c1.setCustomerRef(reference);
        c1.setCustomerName("First Customer");
        c1.setAddressLine1("23");
        c1.setAddressLine2("Piccadilly Road");
        c1.setTown("Manchester");
        c1.setCounty("Chesire");
        c1.setCountry("UK");
        c1.setPostcode("M1 9TG");

        return c1;
    }
}
