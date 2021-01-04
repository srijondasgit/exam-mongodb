package org.gitanjali.exam.Model;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class EntityAuditing implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        // get your user name here
        return Optional.of("abc");
    }

}
