package com.example.grocery.controller;

import com.example.grocery.entity.Customer;
import com.example.grocery.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testList() throws Exception {
        Mockito.when(customerService.list()).thenReturn(Arrays.asList(new Customer()));
        mockMvc.perform(get("/api/customers")).andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        Customer c = new Customer();
        c.setName("Alice");
        Mockito.when(customerService.create(Mockito.any())).thenReturn(c);
        mockMvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(c))).andExpect(status().isOk());
    }
}
