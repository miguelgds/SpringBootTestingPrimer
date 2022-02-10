package es.miguelgsi.springboottestingprimer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"es.miguelgsi.springboottestingprimer.infrastructure.adapters.out"})
public class JPAConfig {
}
