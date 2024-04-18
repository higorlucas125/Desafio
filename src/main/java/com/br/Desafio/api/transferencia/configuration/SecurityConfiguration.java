package com.br.Desafio.api.transferencia.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .oauth2Client()
            .and()
            .oauth2Login()
            .tokenEndpoint()
            .and()
            .userInfoEndpoint();

        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        http
            .authorizeHttpRequests()
            .requestMatchers("/unauthenticated", "/oauth2/**", "/login/**","/h2-console/**").permitAll()
            .anyRequest()
            .fullyAuthenticated()
            .and()
            .logout()
            .logoutSuccessUrl("http://localhost:8080/realms/higorlucas/protocol/openid-connect/logout?redirect_uri=http://localhost:9091/");

        return http.build();
    }
}
