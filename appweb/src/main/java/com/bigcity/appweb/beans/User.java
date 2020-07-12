package com.bigcity.appweb.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author nicolasdotnet
 *
 * User is the registration entity of a user.
 *
 */
@Component
public class User implements Serializable {

    private Long userId;

    private Date userDate;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private Role role;

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
