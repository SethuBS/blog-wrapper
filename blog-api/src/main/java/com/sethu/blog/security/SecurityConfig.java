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
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/api-docs/**").permitAll() // Allow access to Swagger UI and API documentation
                .antMatchers("/api/v1/posts/**", "/api/v1/comments/**").permitAll() // Allow access to all endpoints in PostController and CommentController without authentication
                .antMatchers("/api/v1/blogs/**").hasRole("ADMIN") // Restrict access to "/api/v1/blogs" endpoints to users with the role "ADMIN"
                .anyRequest().authenticated() // Require authentication for other endpoints
                .and()
                .csrf().disable(); // Disable CSRF protection for simplicity (you may want to enable it in production)
    }
}
