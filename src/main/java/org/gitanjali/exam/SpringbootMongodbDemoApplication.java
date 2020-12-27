package org.gitanjali.exam;

import org.gitanjali.exam.model.EntityAuditing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.gitanjali.*;

@SpringBootApplication
@ComponentScan(basePackages = {"org.gitanjali.*"})
@EnableMongoAuditing
public class SpringbootMongodbDemoApplication {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new EntityAuditing();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMongodbDemoApplication.class, args);
    }
}
