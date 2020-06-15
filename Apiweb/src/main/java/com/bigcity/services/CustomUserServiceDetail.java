///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.bigcity.services;
//
//import com.bigcity.dao.IUserRepository;
//import com.bigcity.entity.User;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Objects;
//import java.util.Optional;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author nicolasdotnet
// */
//@Service
//public class CustomUserServiceDetail implements UserDetailsService {
//
//    private static final Logger log = LogManager.getLogger(CustomUserServiceDetail.class);
//
//    @Autowired
//    private IUserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        Objects.requireNonNull(email);
//
//        Optional<User> userFind = userRepository.findByEmail(email);
//
//        if (!userFind.isPresent()) {
//
//            log.error("L'email n'existe pas !");
//            throw new UsernameNotFoundException("L'email n'existe pas !");
//        }
//
//        Collection<GrantedAuthority> role = new ArrayList<>();
//        role.add(new SimpleGrantedAuthority(userFind.get().getRole().getRoleName()));
//
//        System.out.println("loadUserByUsername -> role : " + userFind.get().getRole().getRoleName());
//
//        return new org.springframework.security.core.userdetails.User(email, userFind.get().getPassword(), role);
//    }
//
//}
