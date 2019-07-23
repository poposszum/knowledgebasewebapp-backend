package com.company.knowledgebasebackend;

import com.company.knowledgebasebackend.user.UserEntity;
import com.company.knowledgebasebackend.user.UserRepository;
import com.company.knowledgebasebackend.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void save() {
        UserEntity userEntity = UserEntity.builder()
                .firstName("firstname1")
                .lastName("lastname1")
                .email("email@email.com")
                .password(passwordEncoder.encode("password"))
                .build();
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        assertEquals(userEntity, userService.save(userEntity));
    }
}
