package com.ecommerce.admin.controller;

import com.ecommerce.admin.model.Customer;
import com.ecommerce.admin.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Handler method to display list of customers
    @GetMapping
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "admin/customers/list"; // Ensure "customers/list.html" exists under "templates/admin/"
    }

    // Handler method to show customer form for editing
    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "admin/customers/edit"; // Ensure "customers/edit.html" exists under "templates/admin/"
    }

    // Handler method to process customer update
    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute Customer customer) {
        customer.setId(id); // Ensure the customer id is set
        customerService.updateCustomer(customer);
        return "redirect:/admin/customers"; // Redirect to customer list
    }

    // Handler method to show customer deletion confirmation
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "admin/customers/delete"; // Ensure "customers/delete.html" exists under "templates/admin/"
    }

    // Handler method to process customer deletion
    @PostMapping("/delete/{id}")
    public String confirmDeleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/admin/customers"; // Redirect to customer list
    }
}
