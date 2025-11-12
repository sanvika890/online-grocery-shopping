package com.example.grocery.service;

import com.example.grocery.entity.Customer;
import com.example.grocery.exception.ResourceNotFoundException;
import com.example.grocery.repository.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer c) {
        return customerRepository.save(c);
    }

    public List<Customer> list() {
        return customerRepository.findAll();
    }

    public Customer get(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    public Customer update(Long id, Customer updated) {
        Customer c = get(id);
        c.setName(updated.getName());
        c.setEmail(updated.getEmail());
        c.setAddress(updated.getAddress());
        c.setPhone(updated.getPhone());
        return customerRepository.save(c);
    }

    public void delete(Long id) {
        Customer c = get(id);
        customerRepository.delete(c);
    }
}
