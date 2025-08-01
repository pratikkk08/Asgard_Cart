package com.jtspringproject.JtSpringProject.repositories;

import com.jtspringproject.JtSpringProject.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CartRepository interface for database operations on Cart entities.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    // You could add methods like `findCartsByCustomer(User customer)` here.
}
