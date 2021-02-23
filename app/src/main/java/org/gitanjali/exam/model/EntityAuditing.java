package org.gitanjali.exam.model;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class EntityAuditing implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        // get your user name here
        return Optional.of("abc");
    }

}
