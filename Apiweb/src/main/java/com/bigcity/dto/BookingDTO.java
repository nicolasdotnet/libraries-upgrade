package com.bigcity.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author nicolasdotnet
 */
public class BookingDTO {

    
    @NotEmpty(message = "la réference bibliothécaire n'est pas renseigné")
    @ApiModelProperty(notes = "librarian id")
    private Long librarianId;
    
    @NotEmpty(message = "la réference usagé n'est pas renseigné")
    @ApiModelProperty(notes = "booking user id")
    private Long bookingUserId;
    
    @NotEmpty(message = "la réference livre n'est pas renseigné")
    @ApiModelProperty(notes = "book id")
    private Long bookId;

    public BookingDTO() {
    }

    public Long getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(Long librarianId) {
        this.librarianId = librarianId;
    }

    public Long getBookingUserId() {
        return bookingUserId;
    }

    public void setBookingUserId(Long bookingUserId) {
        this.bookingUserId = bookingUserId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "BookingDTO{" + "librarianId=" + librarianId + ", bookingUserId=" + bookingUserId + ", bookId=" + bookId + '}';
    }
    
    
}
