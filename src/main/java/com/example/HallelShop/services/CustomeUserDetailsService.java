package com.example.HallelShop.services;

import com.example.HallelShop.models.User;
import com.example.HallelShop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j // Добавьте эту аннотацию

public class CustomeUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null || email.trim().isEmpty()) {
            log.error("Email is null or empty");
            throw new UsernameNotFoundException("Email is null or empty");
        }

        User user = userRepository.findByEmail(email.trim());
        if (user == null) {
            log.error("User not found with email: " + email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return user;
    }
}

