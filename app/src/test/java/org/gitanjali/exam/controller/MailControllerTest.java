package org.gitanjali.exam.controller;

import org.gitanjali.exam.config.EmailConfig;
import org.gitanjali.exam.model.RegisterEmail;
import org.gitanjali.exam.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.BindingResult;

import javax.xml.bind.ValidationException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailControllerTest {

    @Mock
    UserRepository userRepository;
    @Mock
    EmailConfig emailConfig;
    @Mock
    BindingResult bindingResult;
    @Mock
    SimpleMailMessage mailMessage;
    @InjectMocks
    MailController mailController;

    @Test(expected = ValidationException.class)
    public void registerEmailTest_ValidationError() throws ValidationException {
        when(bindingResult.hasErrors()).thenReturn(true);
        mailController.registerEmail(new RegisterEmail(), bindingResult);
    }
}
