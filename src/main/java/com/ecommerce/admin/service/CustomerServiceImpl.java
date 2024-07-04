package com.ecommerce.admin.service;

import com.ecommerce.admin.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    // Simulating in-memory database
    private static final Map<Long, Customer> customers = new HashMap<>();

    static {
        customers.put(1L, new Customer(1L, "John Doe", "john@example.com", "1234567890"));
        customers.put(2L, new Customer(2L, "Jane Smith", "jane@example.com", "9876543210"));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customers.get(id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customers.remove(id);
    }
}
