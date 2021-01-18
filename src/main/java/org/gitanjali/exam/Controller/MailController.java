package org.gitanjali.exam.Controller;

import org.gitanjali.exam.Config.EmailConfig;
import org.gitanjali.exam.Entity.User;
import org.gitanjali.exam.Model.RegisterEmail;
import org.gitanjali.exam.Repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/registerEmail")
public class MailController {

    private UserRepository userRepository;

    private EmailConfig emailConfig;

    public MailController(EmailConfig emailConfig, UserRepository userRepository) {
        this.emailConfig = emailConfig;
        this.userRepository = userRepository;
    }


    @PostMapping
    public void registerEmail(@RequestBody RegisterEmail registerEmail,
                              BindingResult bindingResult)
            throws ValidationException {

        if (bindingResult.hasErrors()) {

            throw new ValidationException("Email error");
        }

        //Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort(this.emailConfig.getPort());
        mailSender.setUsername(this.emailConfig.getUsername());
        mailSender.setPassword(this.emailConfig.getPassword());

        //Generate random number
        Random rand = new Random();
        int num = rand.nextInt((9999 - 100) + 1) + 10;

        //Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("admin@gitanjali.org");
        mailMessage.setTo(registerEmail.getEmail());
        mailMessage.setSubject("Gitanjali.org - Verfication Token");
        mailMessage.setText(registerEmail.getName()+" - Your verification token is : "+ num);

        User user = new User (registerEmail.getName(), String.valueOf(num), registerEmail.getEmail(), registerEmail.getRole());

        List<User> users = Arrays.asList(user);
        this.userRepository.saveAll(users);

        // Send mail
        mailSender.send(mailMessage);

    }
}
