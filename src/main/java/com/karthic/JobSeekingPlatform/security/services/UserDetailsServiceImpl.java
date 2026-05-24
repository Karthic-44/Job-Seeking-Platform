package com.karthic.JobSeekingPlatform.security.services;

import com.karthic.JobSeekingPlatform.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.karthic.JobSeekingPlatform.model.Users;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findByUserName(username).orElseThrow
                (()-> new UsernameNotFoundException("User Not Found with username"+username));


        return UserDetailsImpl.build(user);
    }
}
