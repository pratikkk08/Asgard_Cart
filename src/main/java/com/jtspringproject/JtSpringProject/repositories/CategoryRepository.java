package com.jtspringproject.JtSpringProject.repositories;
import com.jtspringproject.JtSpringProject.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Finds a category by its name, ignoring case.
    Category findByNameIgnoreCase(String name);
}