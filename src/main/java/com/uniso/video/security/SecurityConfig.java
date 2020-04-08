package com.uniso.video.security;

import com.uniso.video.filter.ApiKeyAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${api.auth.request.header}")
    private String header;

    @Value("${api.auth.request.value}")
    private String value;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ApiKeyAuthFilter filter = new ApiKeyAuthFilter(header);
        filter.setAuthenticationManager(authentication -> {
            String principal = ((String) authentication.getPrincipal()).trim();
            if (!value.equals(principal)) {
                throw new BadCredentialsException("Api key is not true!");
            }
            authentication.setAuthenticated(true);
            return authentication;
        });

        http
                .antMatcher("/api/**")
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(filter)
                .authorizeRequests().anyRequest().authenticated();

        http.cors();

    }
}
