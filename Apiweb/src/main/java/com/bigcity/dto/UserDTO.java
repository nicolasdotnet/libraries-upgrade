/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author nicolasdotnet
 */
public class UserDTO {

    @Size(min = 3, max = 10, message = "Nom trop long ou trop court. Et oui messages sont plus stylés que ceux de Spring")
    private String firstname;
    
    @Size(min = 3, max = 10, message = "La taille doit etre entre 3 et 10.")
    private String lastname;

    @Email(message = "votre email est erroné.")
    private String email;

    @NotEmpty(message = "votre identifiant n'est pas renseigné.")
    @Size(min = 3, max = 10, message = "La taille doit etre entre 3 et 10.")
    private String username;

    @NotEmpty(message = "votre mot de passe n'est pas renseigné.")
    private String password;

    public UserDTO() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", username=" + username + ", password=" + password + '}';
    }
    
    

}
