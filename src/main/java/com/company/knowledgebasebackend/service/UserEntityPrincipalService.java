package com.company.knowledgebasebackend.service;

import com.company.knowledgebasebackend.user.UserEntity;
import com.company.knowledgebasebackend.user.UserEntityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserEntityPrincipalService implements UserDetailsService {
    private UserEntityRepository userEntityRepository;

    public UserEntityPrincipalService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = this.userEntityRepository.findByUsername(s);

        return new UserEntityPrincipal(userEntity);
    }
}
