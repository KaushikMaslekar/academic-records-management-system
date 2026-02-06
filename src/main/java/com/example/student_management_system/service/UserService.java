package com.example.student_management_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.student_management_system.entity.User;
import com.example.student_management_system.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injecting PasswordEncoder to encode user passwords before saving them to the database.

    public User registerUser(User user) {
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword())); // This line encodes the user's password using the PasswordEncoder before saving it to the database. This is a crucial step for security, as it ensures that plain text passwords are not stored in the database.
        return userRepository.save(user); // This line saves the user to the database using the UserRepository. The save method is a standard method provided by Spring Data repositories that persists the entity to the database and returns the saved entity, which may include additional information such as a generated ID.
    }

    public User findByUsername(String username) { // This method retrieves a user by their username. It uses the UserRepository to find the user and returns it. If the user is not found, it returns null.
        return userRepository.findByUsername(username).orElse(null);
    }
}
