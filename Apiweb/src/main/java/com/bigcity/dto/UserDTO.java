package com.bigcity.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author nicolasdotnet
 */
public class UserDTO {

    @NotEmpty(message = "le prénom n'est pas renseigné")
    @Size(min = 3, max = 10, message = "Nom trop long ou trop court")
    @ApiModelProperty(notes = "firstname for a user")
    private String firstname;
    
    @NotEmpty(message = "le nom n'est pas renseigné")
    @Size(min = 3, max = 10, message = "La taille doit etre entre 3 et 10")
    @ApiModelProperty(notes = "lastname for a user")
    private String lastname;

    @NotEmpty(message = "l'email n'est pas renseigné")
    @Email(message = "l'email est erroné")
    @ApiModelProperty(notes = "email for a user : user id")
    private String email;

    @NotEmpty(message = "le mot de passe n'est pas renseigné")
    @ApiModelProperty(notes = "password for a user")
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password=" + password + '}';
    }
    
    

}
