package com.company.knowledgebasebackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDbFiller implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();

        UserEntity adminUser = UserEntity.builder()
                .lastName("admin1")
                .firstName("admin1")
                .email("admin@admin.com")
                .password(passwordEncoder.encode("admin123"))
                .roles(Arrays.asList("ADMIN", "USER"))
                .build();

        UserEntity userUser = UserEntity.builder()
                .lastName("user1")
                .firstName("user1")
                .email("user@user.com")
                .password(passwordEncoder.encode("user123"))
                .roles(Arrays.asList("USER"))
                .build();

        List<UserEntity> users = Arrays.asList(adminUser, userUser);

        userRepository.saveAll(users);

    }
}
