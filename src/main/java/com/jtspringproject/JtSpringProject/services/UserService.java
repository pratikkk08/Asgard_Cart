package com.jtspringproject.JtSpringProject.services;

import com.jtspringproject.JtSpringProject.dto.UserDto;
import com.jtspringproject.JtSpringProject.mappers.EntityDtoMapper;
import com.jtspringproject.JtSpringProject.models.User;
import com.jtspringproject.JtSpringProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieves all users, converting them to DTOs.
     * @return a list of UserDto objects.
     */
    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
        return EntityDtoMapper.toUserDtoList(userRepository.findAll());
    }

    /**
     * Creates a new user, encrypts their password, and saves them to the database.
     * @param user The User entity from the registration form.
     * @return the saved user as a UserDto.
     */
    @Transactional
    public UserDto addUser(User user) {
        // REFACTORED: This method now returns a DTO for consistency.
        // It correctly takes a User entity to access the raw password for encoding.
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return EntityDtoMapper.toUserDto(savedUser);
    }

    /**
     * Finds a user by their username and returns them as a DTO.
     * @param username the username to search for.
     * @return the found user as a UserDto, or null if not found.
     */
    @Transactional(readOnly = true)
    public UserDto getUserDtoByUsername(String username) {
        return EntityDtoMapper.toUserDto(userRepository.findByUsername(username));
    }

    /**
     * Finds a user by username and returns the raw User entity.
     * This method is required by Spring Security for the authentication process.
     * @param username the username to search for.
     * @return the User entity.
     */
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Checks if a username already exists in the database.
     * @param username the username to check.
     * @return true if the user exists, false otherwise.
     */
    @Transactional(readOnly = true)
    public boolean checkUserExists(String username) {
        // REFACTORED: For better performance, this now uses an exists check.
        // NOTE: You should add `boolean existsByUsername(String username);` to your UserRepository.
        return userRepository.existsByUsername(username);
    }
}