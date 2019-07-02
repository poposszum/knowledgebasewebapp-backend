package com.company.knowledgebasebackend.security;

import com.company.knowledgebasebackend.user.UserEntity;
import com.company.knowledgebasebackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

            if (userEntity == null) throw new UsernameNotFoundException("User not found with email: " + email);

            return new UserPrincipal(userEntity);

    }
}
