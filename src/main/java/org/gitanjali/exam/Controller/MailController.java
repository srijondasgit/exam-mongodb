package org.gitanjali.exam.Controller;

import org.gitanjali.exam.Config.EmailConfig;
import org.gitanjali.exam.Model.RegisterEmail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/registerEmail")
public class MailController {



    private EmailConfig emailConfig;

    public MailController(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
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

        //Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(registerEmail.getEmail());
        mailMessage.setTo("srijondas@gmail.com");
        mailMessage.setSubject("Test email "+registerEmail.getName() );
        mailMessage.setText(registerEmail.getMailBody());

        // Send mail
        mailSender.send(mailMessage);

    }
}
