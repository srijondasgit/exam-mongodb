package org.gitanjali.exam.controller;

import org.gitanjali.exam.config.EmailConfig;
import org.gitanjali.exam.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private UserRepository userRepository;

    private EmailConfig emailConfig;

    public ProfileController(EmailConfig emailConfig, UserRepository userRepository) {
        this.emailConfig = emailConfig;
        this.userRepository = userRepository;
    }

    @GetMapping("/getProfileRole")
    public Collection<? extends GrantedAuthority> getSelfProfile() {
        String username;
        List<String> testIds = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        System.out.println(username);

        return ((UserDetails) principal).getAuthorities();

    }

    @GetMapping("/getProfileName")
    public String getSelfName() {
        String username;
        List<String> testIds = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        System.out.println(username);

        return username;

    }
}
