package com.example.student_management_system.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.student_management_system.entity.User;
import com.example.student_management_system.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService { // This class implements the UserDetailsService interface, which is a core interface in Spring Security. It provides a method to load user-specific data.

    @Autowired
    private UserRepository userRepository; // Injecting the UserRepository to access user data from the database.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // This method is overridden to provide the logic for loading a user by their username. It throws a UsernameNotFoundException if the user is not found.
        User user = userRepository.findByUsername(username) // This line uses the UserRepository to find a user by their username. The findByUsername method is assumed to return an Optional<User>.
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User( // This line creates and returns a new UserDetails object using the Spring Security User class. It takes the username, password, active status, and authorities (roles) of the user to construct the UserDetails object.
                user.getUsername(), // The username of the user, which is used for authentication.
                user.getPassword(), // The password of the user, which is used for authentication. It should be stored in an encoded form in the database.
                Boolean.TRUE.equals(user.getActive()),
                true,
                true,
                true,
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())) //simpleGrantedAuthority is used to convert the role string into a GrantedAuthority object that Spring Security can understand. 
        //for example, if the user has a role of "ADMIN", it will create a SimpleGrantedAuthority with the authority "ADMIN". This allows Spring Security to manage access control based on the user's roles. 
        );
    }
}
