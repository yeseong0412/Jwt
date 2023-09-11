package com.example.log.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.log.domain.UserDetails;

public interface UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}