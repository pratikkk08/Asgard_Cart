package com.jtspringproject.JtSpringProject.mappers;

import com.jtspringproject.JtSpringProject.dto.CategoryDto;
import com.jtspringproject.JtSpringProject.dto.ProductDto;
import com.jtspringproject.JtSpringProject.dto.UserDto;
import com.jtspringproject.JtSpringProject.models.Category;
import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.models.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class for mapping database entities to Data Transfer Objects (DTOs).
 * This ensures that only safe and necessary data is exposed to the client.
 */
public final class EntityDtoMapper {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private EntityDtoMapper() {
    }

    public static UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public static List<UserDto> toUserDtoList(List<User> users) {
        return users.stream().map(EntityDtoMapper::toUserDto).collect(Collectors.toList());
    }

    public static ProductDto toProductDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setImage(product.getImage());
        productDto.setQuantity(product.getQuantity());
        productDto.setPrice(product.getPrice());
        productDto.setWeight(product.getWeight());
        productDto.setDescription(product.getDescription());
        if (product.getCategory() != null) {
            productDto.setCategoryName(product.getCategory().getName());
        }
        return productDto;
    }

    public static List<ProductDto> toProductDtoList(List<Product> products) {
        return products.stream().map(EntityDtoMapper::toProductDto).collect(Collectors.toList());
    }

    public static CategoryDto toCategoryDto(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    public static List<CategoryDto> toCategoryDtoList(List<Category> categories) {
        return categories.stream().map(EntityDtoMapper::toCategoryDto).collect(Collectors.toList());
    }
}