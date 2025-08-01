package com.jtspringproject.JtSpringProject.controller;

import com.jtspringproject.JtSpringProject.services.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public ModelAndView viewCart(HttpSession session) {
        ModelAndView mView = new ModelAndView("cart");
        mView.addObject("cart", cartService.getCart(session));
        return mView;
    }

    @PostMapping("/cart/add/{productId}")
    public String addToCart(@PathVariable("productId") int productId, HttpSession session) {
        cartService.addProductToCart(productId, session);
        return "redirect:/home"; // Or redirect back to the product page
    }

    @PostMapping("/cart/remove/{productId}")
    public String removeFromCart(@PathVariable("productId") int productId, HttpSession session) {
        cartService.removeProductFromCart(productId, session);
        return "redirect:/cart";
    }
}