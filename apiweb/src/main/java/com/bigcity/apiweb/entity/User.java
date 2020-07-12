package com.bigcity.apiweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author nicolasdotnet
 *
 * User is the registration entity of a user.
 *
 */
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "user id")
    private Long userId;

    @JoinColumn(nullable = false)
    @ApiModelProperty(notes = "user registration date")
    private Date userDate;

    @Column(length = 100, nullable = false)
    @ApiModelProperty(notes = "first name from user")
    private String firstname;

    @Column(length = 100, nullable = false)
    @ApiModelProperty(notes = "last name from user")
    private String lastname;

    @Column(nullable = false)
    @ApiModelProperty(notes = "email from user : unique identifier")
    private String email;

    @Column(nullable = false)
    @ApiModelProperty(notes = "password from user")
    private String password;

    @ManyToOne
    @JoinColumn(nullable = false)
    @ApiModelProperty(notes = "role from user")
    private Role role;

    @OneToMany(mappedBy = "bookingUser", fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "booking list from user")
    @JsonIgnore
    private Collection<Booking> bookings;

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getUserDate() {
        return userDate;
    }

    public void setUserDate(Date userDate) {
        this.userDate = userDate;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Collection<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Collection<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", userDate=" + userDate
                + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
                + ", password=" + password + ", role=" + role + '}';
    }

}
