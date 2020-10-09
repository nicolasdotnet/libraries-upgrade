/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.appweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author nicolasdotnet
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthentication userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(userDetailsService).eraseCredentials(false);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); 

        // The pages does not require login
        http.authorizeRequests().antMatchers("/","/signup","/login", "/img/**", "/styles.css", "/bootstrap/**", "/webjars/**").permitAll();
        
        http.authorizeRequests().antMatchers("/user/**").hasAuthority("usager");

        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will be thrown.
        http.exceptionHandling().accessDeniedPage("/403");

        http.formLogin().defaultSuccessUrl("/", true);
        
        http.authorizeRequests().anyRequest().authenticated();

    }

}
