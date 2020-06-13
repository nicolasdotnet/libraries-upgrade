/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.specifications;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author nicolasdotnet
 */
public class UserCriteria {
    
    
    @ApiModelProperty(notes = "first name from user")
    private String firstname;

    @ApiModelProperty(notes = "last name from user")
    private String lastname;

    @ApiModelProperty(notes = "email from user : unique identifier")
    private String email;
    
    @ApiModelProperty(notes = "role from user")
    private String role;

    public UserCriteria() {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
    
    
}
