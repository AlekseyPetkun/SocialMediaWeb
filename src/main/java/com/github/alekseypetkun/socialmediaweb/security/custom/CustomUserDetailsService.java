package com.github.alekseypetkun.socialmediaweb.security.custom;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {

    UserDetails loadUserById(Long userId);
}
