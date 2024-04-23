package com.sethu.blog.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
                        .antMatchers("/api/v1/posts/**", "/api/v1/comments/**").permitAll()
                        .antMatchers("/api/v1/blogs/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .csrf().disable();
    }
}
