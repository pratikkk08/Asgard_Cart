package com.jtspringproject.JtSpringProject.repositories;

import com.jtspringproject.JtSpringProject.models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CartProductRepository for the join table between Cart and Product.
 */
@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {
    // You could add custom queries here, for example, to find all products in a specific cart.
}
