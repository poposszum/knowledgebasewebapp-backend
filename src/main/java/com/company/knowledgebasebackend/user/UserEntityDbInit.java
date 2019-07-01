package com.company.knowledgebasebackend.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserEntityDbInit implements CommandLineRunner {

    private UserEntityRepository userEntityRepository;

    private PasswordEncoder passwordEncoder;

    public UserEntityDbInit(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        this.userEntityRepository.deleteAll();

        UserEntity adminUser = UserEntity.builder()
                .lastName("admin1")
                .firstName("admin1")
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles(Arrays.asList("ADMIN", "USER"))
                .permissions(Arrays.asList("PERM1"))
                .active(1)
                .registrationDate(new Date())
                .build();

        UserEntity userUser = UserEntity.builder()
                .lastName("user1")
                .firstName("user1")
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles(Arrays.asList("USER"))
                .permissions(Arrays.asList("PERM1"))
                .active(1)
                .build();

        List<UserEntity> users = Arrays.asList(adminUser, userUser);

        this.userEntityRepository.saveAll(users);

    }
}
