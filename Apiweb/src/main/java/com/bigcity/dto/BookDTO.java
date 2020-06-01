/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author nicolasdotnet
 */
public class BookDTO {
    
    @NotEmpty(message = "le ISBN n'est pas renseigné.")
    @Size(min = 3, max = 10, message = "ISBN trop long ou trop court.")
    private String isbn;
    
    @NotEmpty(message = "le prénom de l'auteur n'est pas renseigné.")
    @Size(min = 3, max = 10, message = "Prénom trop long ou trop court.")
    private String authorFirstname;

    @NotEmpty(message = "le nom de l'auteur n'est pas renseigné.")
    @Size(min = 3, max = 10, message = "Nom trop long ou trop court.")
    private String authorLastname;
    
    @NotEmpty(message = "le titre du livre n'est pas renseigné.")
    @Size(min = 1, max = 10, message = "Titre du livre trop long ou trop court.")
    private String bookTitle;
    
    @NotEmpty(message = "le nombre de copie n'est pas renseigné.")
    private int copiesAvailable;

    public BookDTO() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthorFirstname() {
        return authorFirstname;
    }

    public void setAuthorFirstname(String authorFirstname) {
        this.authorFirstname = authorFirstname;
    }

    public String getAuthorLastname() {
        return authorLastname;
    }

    public void setAuthorLastname(String authorLastname) {
        this.authorLastname = authorLastname;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    @Override
    public String toString() {
        return "BookDTO{" + "isbn=" + isbn + ", authorFirstname=" + authorFirstname + ", authorLastname=" + authorLastname + ", bookTitle=" + bookTitle + ", copiesAvailable=" + copiesAvailable + '}';
    }
    
    
    
}
