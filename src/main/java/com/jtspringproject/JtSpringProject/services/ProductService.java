package com.jtspringproject.JtSpringProject.services;

import com.jtspringproject.JtSpringProject.dto.ProductDto;
import com.jtspringproject.JtSpringProject.mappers.EntityDtoMapper;
import com.jtspringproject.JtSpringProject.models.Category;
import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.repositories.CategoryRepository;
import com.jtspringproject.JtSpringProject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        // REFACTORED: Corrected constructor name to match the class name.
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all products as a list of DTOs.
     * @return A list of {@link ProductDto}.
     */
    @Transactional(readOnly = true)
    public List<ProductDto> getProducts() {
        return EntityDtoMapper.toProductDtoList(productRepository.findAll());
    }

    /**
     * Retrieves a single product by its ID as a DTO.
     * @param id The ID of the product.
     * @return A {@link ProductDto}, or null if not found.
     */
    @Transactional(readOnly = true)
    public ProductDto getProductDto(int id) {
        return productRepository.findById(id)
                .map(EntityDtoMapper::toProductDto)
                .orElse(null);
    }

    /**
     * Creates a new product from a DTO.
     * @param productDto The DTO containing the product's data.
     * @return The created {@link ProductDto}, or null if the category is invalid.
     */
    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        // REFACTORED: This method now returns a DTO and handles invalid categories.
        Category category = categoryRepository.findByNameIgnoreCase(productDto.getCategoryName());
        if (category == null) {
            // Cannot create a product with a category that doesn't exist.
            return null;
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setWeight(productDto.getWeight());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return EntityDtoMapper.toProductDto(savedProduct);
    }

    /**
     * Updates an existing product from a DTO.
     * @param id The ID of the product to update.
     * @param productDto The DTO with the new data.
     * @return The updated {@link ProductDto}, or null if the product or category is not found.
     */
    @Transactional
    public ProductDto updateProduct(int id, ProductDto productDto) {
        // REFACTORED: This method now returns a DTO.
        Product product = getProduct(id);
        if (product == null) {
            return null; // Product to update not found
        }
        
        Category category = categoryRepository.findByNameIgnoreCase(productDto.getCategoryName());
        if (category == null) {
            return null; // Invalid category
        }
        
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setWeight(productDto.getWeight());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage());
        product.setCategory(category);
        
        Product updatedProduct = productRepository.save(product);
        return EntityDtoMapper.toProductDto(updatedProduct);
    }

    /**
     * Deletes a product by its ID.
     * @param id The ID of the product to delete.
     */
    @Transactional
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
    
 // Add this method to your ProductService
    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByCategoryId(int categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return EntityDtoMapper.toProductDtoList(products);
    }

    /**
     * Internal helper method to fetch the raw Product entity.
     * This should not be exposed outside the service.
     * @param id The product ID.
     * @return The {@link Product} entity, or null if not found.
     */
    private Product getProduct(int id) {
        // REFACTORED: Changed from public to private for proper encapsulation.
        return productRepository.findById(id).orElse(null);
    }
}