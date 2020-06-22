/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.services;

import com.bigcity.beans.User;
import com.bigcity.services.interfaces.IUserService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author nicolasdotnet
 */
@Service
public class CustomUserServiceDetail implements UserDetailsService {

    private static final Logger log = LogManager.getLogger(CustomUserServiceDetail.class);

    @Autowired
    private IUserService iUserService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Objects.requireNonNull(email);

        User userFind = null;
        try {
            userFind = iUserService.getUserByEmail(email);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CustomUserServiceDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("com.bigcity.services.CustomUserServiceDetail.loadUserByUsername()"+userFind.toString());

        if (userFind == null) {

            log.error("L'email n'existe pas !");
            throw new UsernameNotFoundException("L'email n'existe pas !");
        }

        Collection<GrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority(userFind.getRole().getRoleName()));

        System.out.println("role : " + userFind.getRole().getRoleName());

        return new org.springframework.security.core.userdetails.User(email, userFind.getPassword(), role);
    }

}
