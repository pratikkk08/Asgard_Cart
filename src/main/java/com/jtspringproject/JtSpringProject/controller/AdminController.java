package com.jtspringproject.JtSpringProject.controller;

import com.jtspringproject.JtSpringProject.dto.CategoryDto;
import com.jtspringproject.JtSpringProject.dto.ProductDto;
import com.jtspringproject.JtSpringProject.models.User;
import com.jtspringproject.JtSpringProject.services.CategoryService;
import com.jtspringproject.JtSpringProject.services.ProductService;
import com.jtspringproject.JtSpringProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Value("${admin.secret.key}")
    private String secretKey;

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public AdminController(UserService userService, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping({"/", "/dashboard"})
    public String adminHome() {
        return "adminDashboard";
    }

    @GetMapping("/login")
    public String adminLoginPage() {
        return "adminLogin";
    }

    @GetMapping("/register")
    public String adminRegisterPage() {
        return "adminRegister";
    }

    @PostMapping("/register")
    public String registerAdmin(@ModelAttribute User user, @RequestParam("secretKey") String providedKey, RedirectAttributes redirectAttributes) {
        if (!secretKey.equals(providedKey)) {
            redirectAttributes.addFlashAttribute("error", "Invalid Secret Key. Admin account not created.");
            return "redirect:/admin/register";
        }

        if (this.userService.checkUserExists(user.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "Username is already taken.");
            return "redirect:/admin/register";
        }

        user.setRole("ROLE_ADMIN");
        userService.addUser(user);
        redirectAttributes.addFlashAttribute("success", "Admin account created successfully! Please login.");
        return "redirect:/admin/login";
    }

    // --- Category Management ---

    @GetMapping("/categories")
    public String getCategories(Model model) {
        model.addAttribute("categories", this.categoryService.getCategories());
        return "categories";
    }

    @PostMapping("/categories/add")
    public String addCategory(@RequestParam("name") String name, RedirectAttributes redirectAttributes) {
        // REFACTORED: Check the result from the service layer.
        CategoryDto newCategory = this.categoryService.addCategory(name);
        if (newCategory != null) {
            redirectAttributes.addFlashAttribute("success", "Category added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to add category. Name may be blank or already exist.");
        }
        return "redirect:/admin/categories";
    }

    // --- Product Management ---

    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("products", this.productService.getProducts());
        return "products";
    }

    @GetMapping("/products/add")
    public String addProductPage(Model model) {
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("categories", categoryService.getCategories());
        return "productsAdd";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute ProductDto productDto, RedirectAttributes redirectAttributes) {
        // REFACTORED: Check the result from the service layer.
        ProductDto newProduct = this.productService.addProduct(productDto);
        if (newProduct != null) {
            redirectAttributes.addFlashAttribute("success", "Product added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to add product. The selected category may be invalid.");
        }
        return "redirect:/admin/products";
    }

 // UPGRADED: This method now handles both regular and HTMX requests.
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id,
                                RedirectAttributes redirectAttributes,
                                @RequestHeader(value = "HX-Request", required = false) String hxRequest,
                                Model model) {
        productService.deleteProduct(id);

        // If this is an HTMX request, return only the updated product list fragment.
        if (hxRequest != null) {
            redirectAttributes.addFlashAttribute("success", "Product deleted instantly!");
            model.addAttribute("products", productService.getProducts());
            return "fragments/productList :: productList"; // Return the fragment
        }

        // Otherwise, do a full page redirect for normal requests.
        redirectAttributes.addFlashAttribute("success", "Product deleted successfully!");
        return "redirect:/admin/products";
    }

    @GetMapping("/products/update/{id}")
    public String updateProductPage(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        ProductDto productDto = this.productService.getProductDto(id);
        if (productDto == null) {
            redirectAttributes.addFlashAttribute("error", "Product not found!");
            return "redirect:/admin/products";
        }
        model.addAttribute("productDto", productDto);
        model.addAttribute("categories", this.categoryService.getCategories());
        return "productsUpdate";
    }

    @PostMapping("/products/update/{id}")
    public String updateProduct(@PathVariable("id") int id, @ModelAttribute ProductDto productDto, RedirectAttributes redirectAttributes) {
        // REFACTORED: Check the result from the service layer.
        ProductDto updatedProduct = this.productService.updateProduct(id, productDto);
        if (updatedProduct != null) {
            redirectAttributes.addFlashAttribute("success", "Product updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to update product. The category may be invalid.");
        }
        return "redirect:/admin/products";
    }

    // --- Customer Management ---

    @GetMapping("/customers")
    public String getCustomers(Model model) {
        model.addAttribute("customers", this.userService.getUsers());
        return "displayCustomers";
    }
    

@GetMapping("/categories/delete/{id}")
public String deleteCategory(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
    try {
        categoryService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("success", "Category deleted successfully!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Cannot delete category as it is currently linked to one or more products.");
    }
    return "redirect:/admin/categories";
}
}