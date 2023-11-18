package com.example.test_spring_boot.Configuration.Security;

import com.example.test_spring_boot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;

public class UserDetailsServiceCustom implements org.springframework.security.core.userdetails.UserDetailsService{
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return new UserDetailCustom(userRepository.getByUsername(username));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
