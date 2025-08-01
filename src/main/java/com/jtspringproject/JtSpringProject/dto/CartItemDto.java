package com.jtspringproject.JtSpringProject.dto;

public class CartItemDto {
    private ProductDto product;
    private int quantity;

    // Constructor, Getters, and Setters
    public CartItemDto(ProductDto product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    public ProductDto getProduct() { return product; }
    public void setProduct(ProductDto product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}