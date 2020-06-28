/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.security;

import com.bigcity.beans.User;
import com.bigcity.dto.LoginDTO;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nicolasdotnet
 */
@Component
public class CustomAuthentication implements AuthenticationProvider {

    private static final Logger log = LogManager.getLogger(CustomAuthentication.class);

    @Autowired
    RestTemplate restTemplate;
    
    private HttpHeaders headers = new HttpHeaders();

    @Value("${api.server.port}")
    private String serverPort;

    private String baseUrl = "http://localhost:";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = null;
        
        try {
            user = login(email, password);
        } catch (Exception ex) {
            
            throw new BadCredentialsException(ex.getMessage());
        }

        if (user != null) {

            Collection<GrantedAuthority> role = new ArrayList<>();
            role.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));

            return new UsernamePasswordAuthenticationToken(email, password, role);

        } else {

            throw new BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);

    }

    public User login(String email, String password) throws Exception {

        URI uri = new URI(baseUrl + serverPort + "/api/user/login");
        
        // add basic authentication header
        headers.setBasicAuth("nicolas.desdevises@yahoo.com", "123");

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail(email);
        loginDTO.setPassword(password);

        HttpEntity requestEntity = new HttpEntity(loginDTO,headers);

        ResponseEntity<User> result = restTemplate.postForEntity(uri, requestEntity, User.class);

        return result.getBody();

    }

}
