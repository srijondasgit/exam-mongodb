package org.gitanjali.exam.service;

import org.gitanjali.exam.entity.User;
import org.gitanjali.exam.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @Test
    public void loadUserByUsernameTest1() {
        User testUser = new User("username", "password", "email", "role");
        when(userRepository.findByEmail(anyString())).thenReturn(testUser);
        org.springframework.security.core.userdetails.User response =
                new org.springframework.security.core.userdetails.User("email", "password",
                                                                       Arrays.asList(
                                                                               new SimpleGrantedAuthority("role")));
        assertEquals(response, customUserDetailsService.loadUserByUsername(anyString()));
    }
}
