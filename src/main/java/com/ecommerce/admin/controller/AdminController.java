package com.ecommerce.admin.controller;

import com.ecommerce.admin.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CustomerService customerService;

    public AdminController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        // Example logic to populate dashboard data
        int customerCount = customerService.getAllCustomers().size();
        model.addAttribute("customerCount", customerCount);
        return "admin/dashboard"; // Ensure "admin/dashboard.html" exists under templates
    }

    @GetMapping("/orders")
    public String adminOrders() {
        // Add logic here if needed
        return "admin/orders/list"; // Ensure "admin/orders.html" exists under templates
    }
}
