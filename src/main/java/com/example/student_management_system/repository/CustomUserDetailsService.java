package com.example.student_management_system.repository;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.student_management_system.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService { // This class implements the UserDetailsService interface, which is a core interface in Spring Security. It provides a method to load user-specific data.

    @Autowired
    private UserRepository userRepository; // Injecting the UserRepository to access user data from the database.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // This method is overridden to provide the logic for loading a user by their username. It throws a UsernameNotFoundException if the user is not found.
        User user = userRepository.findByUsername(username) // This line uses the UserRepository to find a user by their username. The findByUsername method is assumed to return an Optional<User>.
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Boolean.TRUE.equals(user.getActive()),
                true,
                true,
                true,
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())) //simpleGrantedAuthority is used to convert the role string into a GrantedAuthority object that Spring Security can understand.
        );
    }
}
