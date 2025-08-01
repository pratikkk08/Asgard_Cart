package com.jtspringproject.JtSpringProject.controller;

import com.jtspringproject.JtSpringProject.dto.CategoryDto;
import com.jtspringproject.JtSpringProject.dto.ProductDto;
import com.jtspringproject.JtSpringProject.dto.UserDto;
import com.jtspringproject.JtSpringProject.models.User;
import com.jtspringproject.JtSpringProject.services.CategoryService;
import com.jtspringproject.JtSpringProject.services.ProductService;
import com.jtspringproject.JtSpringProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public UserController(UserService userService, ProductService productService, CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String landingPage() {
        return "index";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("products", this.productService.getProducts());
        model.addAttribute("categories", this.categoryService.getCategories());
        return "home";
    }

    @GetMapping("/login")
    public String userLoginPage() {
        return "userLogin";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/newuserregister")
    public String registerNewUser(@ModelAttribute User user, RedirectAttributes redirectAttributes, Model model) {
        // CORRECTED: Restored the full implementation for this method.
        boolean exists = this.userService.checkUserExists(user.getUsername());

        if (exists) {
            model.addAttribute("error", "Username '" + user.getUsername() + "' is already taken. Please choose another.");
            return "register";
        }

        user.setRole("ROLE_USER");
        this.userService.addUser(user);

        redirectAttributes.addFlashAttribute("success", "Registration successful! Please log in.");
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Authentication authentication) {
        // CORRECTED: Restored the full implementation for this method.
        String username = authentication.getName();
        UserDto user = userService.getUserDtoByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/products/category/{id}")
    public String getProductsByCategory(@PathVariable("id") int id, Model model) {
        List<ProductDto> products = productService.getProductsByCategoryId(id);
        CategoryDto category = categoryService.getCategory(id);
        
        model.addAttribute("categories", this.categoryService.getCategories());
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        return "home";
    }
}