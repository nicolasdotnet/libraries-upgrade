/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author nicolasdotnet
 */
public class LoginDTO {

    @NotEmpty(message = "l'email n'est pas renseigné")
    @Email(message = "l'email est erroné")
    @ApiModelProperty(notes = "email for a user : user id")
    private String email;

    @NotEmpty(message = "le mot de passe n'est pas renseigné")
    @ApiModelProperty(notes = "password for a user")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

}
