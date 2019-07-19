package com.company.knowledgebasebackend.user;

import com.company.knowledgebasebackend.auth.PasswordResetKey;
import com.company.knowledgebasebackend.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

            if (userEntity == null) throw new UsernameNotFoundException("User not found with email: " + email);

            return new UserPrincipal(userEntity);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public PasswordResetKey findResetKey(String resetKey) {
        List<UserEntity> userEntityList = userRepository.findAll();

        for (UserEntity user : userEntityList){
            if (user.getPasswordResetKey() != null && user.getPasswordResetKey().getResetKey().equals(resetKey)) {
                return user.getPasswordResetKey();
            }
        }
        return null;
    }

    public UserEntity findUserByResetKey(String resetKey) {
        List<UserEntity> userEntityList = userRepository.findAll();

        for (UserEntity user : userEntityList){
            if (user.getPasswordResetKey().getResetKey().equals(resetKey)) {
                return user;
            }
        }
        return null;
    }
}

