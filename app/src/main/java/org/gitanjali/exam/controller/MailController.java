package org.gitanjali.exam.controller;

import org.gitanjali.exam.config.EmailConfig;
import org.gitanjali.exam.entity.User;
import org.gitanjali.exam.model.RegisterEmail;
import org.gitanjali.exam.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

@RestController
@RequestMapping("/mail")
public class MailController {

    private UserRepository userRepository;

    private EmailConfig emailConfig;

    public MailController(EmailConfig emailConfig, UserRepository userRepository) {
        this.emailConfig = emailConfig;
        this.userRepository = userRepository;
    }


    @PostMapping("/register")
    public String registerEmail(@RequestBody RegisterEmail registerEmail,
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

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        //Generate random number
        Random rand = new Random();
        int num = rand.nextInt((9999 - 100) + 1) + 10;

        //Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("admin@gitanjali.org");
        mailMessage.setTo(registerEmail.getEmail());
        mailMessage.setSubject("Gitanjali.org - Verfication Token");
        mailMessage.setText(registerEmail.getName() + " - Your verification token is : " + num);


        if(this.userRepository.findByEmail(registerEmail.getEmail())!=null){
            this.userRepository.delete(this.userRepository.findByEmail(registerEmail.getEmail()));

        }

        User user = new User(registerEmail.getName(), String.valueOf(num), registerEmail.getEmail(), registerEmail.getRole());
        List<User> users = Arrays.asList(user);
        this.userRepository.saveAll(users);

        // Send mail
        mailSender.send(mailMessage);

        return "Successfully registered : " + registerEmail.getName();

    }

    @PostMapping("/reset")
    public ResponseEntity<String>  resetPassword(@RequestBody RegisterEmail registerEmail){

        //Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort(this.emailConfig.getPort());
        mailSender.setUsername(this.emailConfig.getUsername());
        mailSender.setPassword(this.emailConfig.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        //Generate random number
        Random rand = new Random();
        int num = rand.nextInt((9999 - 100) + 1) + 10;

        //Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("admin@gitanjali.org");
        mailMessage.setTo(registerEmail.getEmail());
        mailMessage.setSubject("Gitanjali.org - Password reset");
        mailMessage.setText(registerEmail.getName() + " - Your verification token is : " + num);


        if(this.userRepository.findByEmail(registerEmail.getEmail())==null) {

            return new ResponseEntity<>("Email not found", HttpStatus.OK);

        } else {
            User user = this.userRepository.findByEmail(registerEmail.getEmail());
            user.setPassword(String.valueOf(num));
            this.userRepository.save(user);

            mailSender.send(mailMessage);
            return new ResponseEntity<>("Password updated, please check email", HttpStatus.OK);

        }



    }


}
