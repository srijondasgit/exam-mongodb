package org.gitanjali.exam.controller;

import org.gitanjali.exam.repository.TestRepository;
import org.gitanjali.exam.util.JwtUtil;
import org.javers.core.Javers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    TestRepository testRepository;
    @Mock
    JwtUtil jwtUtil;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    Javers javers;
    @InjectMocks
    UserController userController;

    @Test
    public void getAllTest() throws ValidationException {
        when(testRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0, userController.getAll().size());
    }

    @Test(expected = Exception.class)
    public void generateTokenTest_Exception() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new Exception());
    }

}
