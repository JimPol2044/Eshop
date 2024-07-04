package com.ecommerce.admin.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Collection;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String displayLoginPage(Model model) {
        model.addAttribute("username", ""); // Pass an empty username field to the model
        return "login"; // Return login template
    }

    @PostMapping("/login")
    public String loginSubmit(Authentication authentication, Model model) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                return "redirect:/admin/dashboard"; // Redirect admin to admin dashboard
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                return "redirect:/"; // Redirect user to home page (adjust as needed)
            }
        }
        model.addAttribute("error", "Invalid username or password");
        return "login"; // Return login template with error message
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login"; // Redirect to login after logout
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Return the name of your register.html file
    }
}
