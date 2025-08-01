package com.jtspringproject.JtSpringProject.services;

import com.jtspringproject.JtSpringProject.dto.CategoryDto;
import com.jtspringproject.JtSpringProject.mappers.EntityDtoMapper;
import com.jtspringproject.JtSpringProject.models.Category;
import com.jtspringproject.JtSpringProject.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all categories as a list of DTOs.
     * @return A list of {@link CategoryDto}.
     */
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories() {
        return EntityDtoMapper.toCategoryDtoList(categoryRepository.findAll());
    }

    /**
     * Adds a new category after validating the name.
     * @param name The name of the new category.
     * @return The created {@link CategoryDto}, or null if the name is invalid or already exists.
     */
    @Transactional
    public CategoryDto addCategory(String name) {
        // REFACTORED: Add validation to prevent blank or duplicate category names.
        if (!StringUtils.hasText(name) || categoryRepository.findByNameIgnoreCase(name) != null) {
            return null; // Or throw a custom exception
        }
        Category category = new Category();
        category.setName(name);
        Category savedCategory = categoryRepository.save(category);
        return EntityDtoMapper.toCategoryDto(savedCategory);
    }

    /**
     * Deletes a category by its ID.
     * @param id The ID of the category to delete.
     */
    @Transactional
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    /**
     * Retrieves a single category by its ID as a DTO.
     * @param id The ID of the category.
     * @return A {@link CategoryDto}, or null if not found.
     */
    @Transactional(readOnly = true)
    public CategoryDto getCategory(int id) {
        // REFACTORED: This method now correctly returns a DTO.
        return categoryRepository.findById(id)
                .map(EntityDtoMapper::toCategoryDto)
                .orElse(null);
    }

    /**
     * Updates an existing category's name.
     * @param id The ID of the category to update.
     * @param name The new name for the category.
     * @return The updated {@link CategoryDto}, or null if the category was not found.
     */
    @Transactional
    public CategoryDto updateCategory(int id, String name) {
        // REFACTORED: This method now finds the entity, updates it, and returns a DTO.
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setName(name);
            Category updatedCategory = categoryRepository.save(category);
            return EntityDtoMapper.toCategoryDto(updatedCategory);
        }
        
        // Return null or throw a specific "NotFoundException"
        return null;
    }
}