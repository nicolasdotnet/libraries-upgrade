 package com.bigcity.apiweb.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author nicolasdotnet
 */
public class BookingDTO {

    
    @NotEmpty(message = "la réference bibliothécaire n'est pas renseigné")
    @ApiModelProperty(notes = "librarian email")
    private String librarianEmail;
    
    @NotEmpty(message = "la réference usagé n'est pas renseigné")
    @ApiModelProperty(notes = "booking user email")
    private String bookingUserEmail;
    
    @NotEmpty(message = "la réference livre n'est pas renseigné")
    @ApiModelProperty(notes = "book Isbn")
    private String bookIsbn;

    public BookingDTO() {
    }

    public String getLibrarianEmail() {
        return librarianEmail;
    }

    public void setLibrarianEmail(String librarianEmail) {
        this.librarianEmail = librarianEmail;
    }

    public String getBookingUserEmail() {
        return bookingUserEmail;
    }

    public void setBookingUserEmail(String bookingUserEmail) {
        this.bookingUserEmail = bookingUserEmail;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    @Override
    public String toString() {
        return "BookingDTO{" + "librarianEmail=" + librarianEmail + ", bookingUserEmail=" + bookingUserEmail + ", bookIsbn=" + bookIsbn + '}';
    }


    
    
}
