package com.motaharinia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import th.co.geniustree.springdata.jpa.repository.support.JpaSpecificationExecutorWithProjectionImpl;

@SpringBootApplication(scanBasePackages = {"com.motaharinia"})
@EnableJpaRepositories(repositoryBaseClass = JpaSpecificationExecutorWithProjectionImpl.class)
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JwtAuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtAuthorizationServerApplication.class, args);
    }

}
