package com.ecommerce.admin.controller;

import com.ecommerce.admin.model.CheckoutForm;
import com.ecommerce.admin.model.Order;
import com.ecommerce.admin.model.OrderItem;
import com.ecommerce.admin.model.Product;
import com.ecommerce.admin.service.OrderService;
import com.ecommerce.admin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/checkout")
@SessionAttributes("cart")
public class CheckoutController {

    private final ProductService productService;
    private final OrderService orderService;

    @Autowired
    public CheckoutController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    // Display checkout form
    @GetMapping
    public String checkoutForm(Model model) {
        model.addAttribute("checkoutForm", new CheckoutForm());
        return "user/products/checkout"; // Ensure "checkout.html" exists under "templates/products/"
    }

    // Process checkout
    @PostMapping("/process")
    public String processCheckout(@ModelAttribute("checkoutForm") CheckoutForm checkoutForm, HttpSession session) {
        // Retrieve cart from session safely using generics
        Object sessionObj = session.getAttribute("cart");

        if (!(sessionObj instanceof List)) {
            // Handle case where session attribute "cart" is not a List<Product>
            return "redirect:/products/cart"; // Redirect to cart page or handle error appropriately
        }

        @SuppressWarnings("unchecked")
        List<Product> cart = (List<Product>) sessionObj; // Safe cast after type check

        if (cart.isEmpty()) {
            // Handle case where cart is empty
            return "redirect:/products/cart"; // Redirect to cart page or handle error appropriately
        }

        // Assuming you have a method to get the currently logged-in user's ID
        Long userId = getCurrentUserId();

        // Create a new Order
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus("Pending");
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (Product product : cart) {
            int currentStock = product.getStockQuantity();
            int quantityInCart = product.getQuantity();
            if (quantityInCart > currentStock) {
                // Handle case where cart has more quantity than available stock
                return "redirect:/checkout?error=quantity";
            }
            // Reduce stock quantity
            product.setStockQuantity(currentStock - quantityInCart);
            productService.saveOrUpdate(product); // Save or update product

            // Create OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantityInCart);
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);

            totalAmount += product.getPrice() * quantityInCart;
        }

        order.setOrderItems(orderItems);
        order.setTotal(totalAmount);

        // Save the order to the database
        orderService.saveOrder(order);

        // Clear cart after successful checkout
        session.removeAttribute("cart");

        return "redirect:/checkout/success";
    }

    // Success page after checkout
    @GetMapping("/success")
    public String checkoutSuccess() {
        return "user/products/checkout_success"; // Ensure "checkout_success.html" exists under "templates/products/"
    }

    // Placeholder method to get the current user's ID
    private Long getCurrentUserId() {
        // Implement logic to retrieve the currently logged-in user's ID
        return 1L; // Example: return a dummy user ID
    }
}
