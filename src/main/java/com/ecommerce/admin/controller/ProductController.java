package com.ecommerce.admin.controller;

import com.ecommerce.admin.model.Product;
import com.ecommerce.admin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
@SessionAttributes("cart")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // List all products
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "user/products/list"; // Ensure "list.html" exists under "templates/user/products/"
    }


    // Show the product form for adding a new product or editing an existing one
    @GetMapping("/form")
    public String showForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        Product product = (id != null) ? productService.getProductById(id) : new Product();
        model.addAttribute("product", product);
        return "user/products/form"; // Ensure "form.html" exists under "templates/user/products/"
    }

    // Save a product (Add or Update)
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    // Delete a product by ID
    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    // Show the product form for editing
    @GetMapping("/{id}/form")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            // Handle case where product with given id is not found, perhaps return a custom error page or redirect
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "user/products/form"; // Ensure "form.html" exists under "templates/user/products/"
    }

    // Add a product to the cart
    @PostMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {
        Product product = productService.getProductById(id);
        if (product != null) {
            addProductToCart(product, session);
        }
        return "redirect:/products/cart";
    }

    // Helper method to add a product to the cart
    private void addProductToCart(Product product, HttpSession session) {
        List<Product> cart = getOrCreateCart(session);
        Optional<Product> existingProduct = cart.stream().filter(p -> p.getId().equals(product.getId())).findFirst();
        if (existingProduct.isPresent()) {
            existingProduct.get().setQuantity(existingProduct.get().getQuantity() + 1); // Increment quantity if already in cart
        } else {
            product.setQuantity(1); // Set initial quantity to 1
            cart.add(product);
        }
        session.setAttribute("cart", cart); // Update cart in session
    }

    // Initialize or retrieve the cart from session
    @ModelAttribute("cart")
    public List<Product> cart() {
        return new ArrayList<>(); // Return a new empty ArrayList for the cart
    }

    // Helper method to get or create cart from session
    @SuppressWarnings("unchecked")
    private List<Product> getOrCreateCart(HttpSession session) {
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
