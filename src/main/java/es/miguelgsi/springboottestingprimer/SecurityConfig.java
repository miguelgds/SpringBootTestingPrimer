package es.miguelgsi.springboottestingprimer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled=true, securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorize -> authorize
                .mvcMatchers(HttpMethod.GET, "/v1/shopping-cart/*/price").permitAll()
                .mvcMatchers("/h2-console/**").permitAll()
                .mvcMatchers("/**").authenticated())
            .httpBasic();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails nonActiveUser = User.withDefaultPasswordEncoder()
                .username("user1")
                .password("user1")
                .roles("NON_ACTIVE_USER")
                .build();

        UserDetails activeUser = User.withDefaultPasswordEncoder()
                .username("user2")
                .password("user2")
                .roles("ACTIVE_USER")
                .build();

        return new InMemoryUserDetailsManager(nonActiveUser, activeUser);
    }
}
