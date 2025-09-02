package com.promosim.gestionparc.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
        .authorizeHttpRequests(auth -> auth.requestMatchers("/", "/login", "/css/**", "/js/**").permitAll()
        .anyRequest().authenticated()
        )
        .formLogin(form -> form.loginPage("/login")
        .defaultSuccessUrl("/gestion", true)
        .failureUrl("/login?error=true")
        .permitAll()
        )
        .logout(logout -> logout.permitAll());
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User
        .withUsername("Youcef")
        .password("{noop}password")
        .roles("USER")
        .build();

        UserDetails admin = User
        .withUsername("admin")
        .password("{noop}admin")
        .roles("ADMIN")
        .build();

        return new InMemoryUserDetailsManager(user, admin);
    }


}
