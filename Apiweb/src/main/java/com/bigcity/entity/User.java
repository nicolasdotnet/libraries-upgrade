package com.bigcity.entity;

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
    private Long userId;

    @JoinColumn(nullable = false)
    private Date userDate;

    @Column(length = 100, nullable = false)
    private String firstname;

    @Column(length = 100, nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "bookingUser", fetch = FetchType.LAZY)
    private Collection<Booking> bookings;

//    @OneToMany(mappedBy = "librarian", fetch = FetchType.LAZY)
//    private Collection<Booking> librarianTasks;

//    @OneToMany(mappedBy = "librarian", fetch = FetchType.LAZY)
//    private Collection<Book> books;

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

//    public Collection<Booking> getLibrarianTasks() {
//        return librarianTasks;
//    }
//
//    public void setLibrarianTasks(Collection<Booking> librarianTasks) {
//        this.librarianTasks = librarianTasks;
//    }

//    public Collection<Book> getBooks() {
//        return books;
//    }
//
//    public void setBooks(Collection<Book> books) {
//        this.books = books;
//    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", userDate=" + userDate +
                ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email +
                ", password=" + password + ", role=" + role + '}';
    }
    
    

}
