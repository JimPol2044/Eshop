package com.ecommerce.admin.controller;

import com.ecommerce.admin.model.Product;
import com.ecommerce.admin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;

    @Autowired
    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    // List all products (admin)
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/products/list"; // Ensure "admin/products/list.html" exists under templates
    }

    // Add a product (admin)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/products/form"; // Ensure "admin/products/form.html" exists under templates
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            // Handle validation errors
            return "admin/products/form";
        }

        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    // Edit a product (admin)
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            // Handle product not found scenario
            return "redirect:/admin/products";
        }
        model.addAttribute("product", product);
        return "admin/products/form"; // Ensure "admin/products/form.html" exists under templates
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable Long id, @ModelAttribute("product") @Valid Product product,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Handle validation errors
            model.addAttribute("product", product);
            return "admin/products/form";
        }

        // Ensure the product being updated has the correct ID
        if (!id.equals(product.getId())) {
            // Handle mismatch error
            return "redirect:/admin/products";
        }

        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    // Delete a product (admin)
    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
