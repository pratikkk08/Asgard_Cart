package com.jtspringproject.JtSpringProject.repositories;

import com.jtspringproject.JtSpringProject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    // Add this line for a more efficient existence check
    boolean existsByUsername(String username);
}