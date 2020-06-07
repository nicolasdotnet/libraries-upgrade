/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author nicolasdotnet
 */
public class BookSearchDTO {
    
    @NotEmpty(message = "le ISBN n'est pas renseigné.")
    @Size(min = 3, max = 10, message = "ISBN trop long ou trop court.")
    @ApiModelProperty(notes = "isbn number")
    private String isbn;

    @NotEmpty(message = "le nom de l'auteur n'est pas renseigné.")
    @Size(min = 3, max = 10, message = "Nom trop long ou trop court.")
    @ApiModelProperty(notes = "book author")
    private String author;

    @NotEmpty(message = "le titre du livre n'est pas renseigné.")
    @Size(min = 1, max = 10, message = "Titre du livre trop long ou trop court.")
    @ApiModelProperty(notes = "book title")
    private String bookTitle;

    public BookSearchDTO() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    
}
