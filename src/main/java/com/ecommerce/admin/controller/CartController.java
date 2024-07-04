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

@Controller
@RequestMapping("/products")
@SessionAttributes("cart")
public class CartController {

    private final ProductService productService;

    @Autowired
    public CartController(ProductService productService) {
        this.productService = productService;
    }

    // View the shopping cart
    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        List<Product> cartItems = getOrCreateCart(session);
        model.addAttribute("cartItems", cartItems);
        return "user/products/cart"; // Ensure "cart.html" exists under "templates/products/"
    }

    // Add a product to the cart
    @GetMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {
        List<Product> cart = getOrCreateCart(session);
        Product product = productService.getProductById(id);

        if (product != null) {
            boolean productExists = false;
            for (Product item : cart) {
                if (item.getId().equals(product.getId())) {
                    item.setQuantity(item.getQuantity() + 1);
                    productExists = true;
                    break;
                }
            }
            if (!productExists) {
                product.setQuantity(1);
                cart.add(product);
            }
            session.setAttribute("cart", cart); // Update cart in session
        }
        return "redirect:/products/cart";
    }

    // Remove a product from the cart
    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id, HttpSession session) {
        List<Product> cart = getOrCreateCart(session);
        cart.removeIf(product -> product.getId().equals(id));
        session.setAttribute("cart", cart); // Update cart in session
        return "redirect:/products/cart";
    }

    // Update quantity of a product in the cart
    @PostMapping("/cart/updateQuantity/{id}")
    public String updateQuantity(@PathVariable Long id, @RequestParam("quantity") int quantity, HttpSession session) {
        List<Product> cart = getOrCreateCart(session);
        cart.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .ifPresent(product -> product.setQuantity(quantity));
        session.setAttribute("cart", cart); // Update cart in session
        return "redirect:/products/cart";
    }

    // Clear the cart
    @GetMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart"); // Clear cart in session
        return "redirect:/products";
    }

    // Initialize or retrieve the cart from session
    @ModelAttribute("cart")
    public List<Product> cartItems() {
        return new ArrayList<>(); // Return a new empty ArrayList initially
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
