package com.ceruleansource.SmoothieWebsite.backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
@EnableAutoConfiguration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Vaadin has built in CSRF already
        http.csrf().disable()
                // Authorize requests
                .authorizeRequests()
                // Allow all flow internal requests
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                // Allow all requests by logged in users
                .anyRequest().authenticated()
                // Configure login page
                .and().formLogin().loginPage("/login").permitAll().loginProcessingUrl("/login")
                .and().logout();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                // Vaadin Flow static resources //
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest //
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",

                // (development mode) static resources //
                "/frontend/**",

                // (development mode) webjars //
                "/webjars/**",

                // (production mode) static resources //
                "/frontend-es5/**", "/frontend-es6/**");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
