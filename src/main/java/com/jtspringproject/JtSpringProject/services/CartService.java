// REFACTORED: This service now manages a session-based cart.
package com.jtspringproject.JtSpringProject.services;

import com.jtspringproject.JtSpringProject.dto.CartDto;
import com.jtspringproject.JtSpringProject.dto.CartItemDto;
import com.jtspringproject.JtSpringProject.dto.ProductDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "shoppingCart";
    private final ProductService productService;

    @Autowired
    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public CartDto getCart(HttpSession session) {
        CartDto cart = (CartDto) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new CartDto();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        updateCartTotal(cart);
        return cart;
    }

    public void addProductToCart(int productId, HttpSession session) {
        CartDto cart = getCart(session);
        ProductDto product = productService.getProductDto(productId);

        if (product == null) return;

        // Check if product is already in cart
        for (CartItemDto item : cart.getItems()) {
            if (item.getProduct().getId() == productId) {
                item.setQuantity(item.getQuantity() + 1);
                updateCartTotal(cart);
                return;
            }
        }

        // If not in cart, add as a new item
        cart.getItems().add(new CartItemDto(product, 1));
        updateCartTotal(cart);
    }

    public void removeProductFromCart(int productId, HttpSession session) {
        CartDto cart = getCart(session);
        cart.getItems().removeIf(item -> item.getProduct().getId() == productId);
        updateCartTotal(cart);
    }

    private void updateCartTotal(CartDto cart) {
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        cart.setTotal(total);
    }
}