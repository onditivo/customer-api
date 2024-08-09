package com.esg.exercise.customer.controller;

import com.esg.exercise.customer.model.Customer;
import com.esg.exercise.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    private List<Customer> customers = Collections.emptyList();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        Customer c1 = new Customer();
        c1.setCustomerName("First Customer");
        c1.setAddressLine1("23");
        c1.setAddressLine2("Piccadilly Road");
        c1.setTown("Manchester");
        c1.setCounty("Chesire");
        c1.setCountry("UK");
        c1.setPostcode("M1 9TG");

        Customer c2 = new Customer();
        c2.setCustomerRef("cust200");
        c2.setCustomerName("Bill Oreily");
        c2.setAddressLine1("67");
        c2.setAddressLine2("Fox Road");
        c2.setTown("Manchester");
        c2.setCounty("Chesire");
        c2.setCountry("UK");
        c2.setPostcode("M2 4DC");

        customers = List.of(c1, c2);
    }

    // invalid input use case - customer reference should not be empty
    @Test
    public void shouldSaveCustomerReturnsBadRequest() throws Exception {
        String request = objectMapper.writeValueAsString(customers.get(0));

        mockMvc.perform(post("/api/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isBadRequest());
    }

    //valid input use case - no validation error and the saved customer information is same as the supplied one
    @Test
    public void shouldSaveCustomerReturnsCreated() throws Exception {

        // given
        var customer1 = customers.get(1);
        val request = objectMapper.writeValueAsString(customer1);

        //when
        when(customerService.saveOrUpdateCustomer(customer1)).thenReturn(customer1);

        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(content().json(request));
    }

    // Use case - No customer with given customer reference
    @Test
    public void shouldNotFindCustomerByReference() throws Exception {
        mockMvc.perform(get("/api/v1/customer/cust400"))
                .andExpect(status().isNotFound());
    }

    // Use case - existing customer should be found and returned.
    @Test
    public void shouldFindCustomerByReference() throws Exception {
        // given
        var customer1 = customers.get(1);
        String request = objectMapper.writeValueAsString(customers.get(1));
        when(customerService.findCustomer("cust200")).thenReturn(Optional.of(customer1));

        //when
        mockMvc.perform(get("/api/v1/customer/cust200"))
                .andExpect(status().isOk())
                .andExpect(content().json(request));
    }
}
