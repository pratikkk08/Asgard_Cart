package com.jtspringproject.JtSpringProject.repositories;

import com.jtspringproject.JtSpringProject.models.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository interface for database operations on Product entities.
 * Extends JpaRepository to provide standard CRUD functionality.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Custom query methods can be added here if needed in the future.
	// Add this method to your ProductRepository interface
	List<Product> findByCategoryId(int categoryId);
}
