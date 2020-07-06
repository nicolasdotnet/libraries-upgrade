package com.bigcity.apiweb.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nicolasdotnet
 */
public class BookDTO {

    @NotEmpty(message = "le ISBN n'est pas renseigné")
    @Size(min = 3, max = 10, message = "ISBN trop long ou trop court")
    @ApiModelProperty(notes = "isbn number")
    private String isbn;

    @NotEmpty(message = "l'autheur de l'auteur n'est pas renseigné")
    @Size(min = 3, max = 10, message = "Prénom trop long ou trop court")
    @ApiModelProperty(notes = "book author")
    private String author;

    @NotEmpty(message = "le titre du livre n'est pas renseigné")
    @Size(min = 1, max = 10, message = "Titre du livre trop long ou trop court")
    @ApiModelProperty(notes = "book title")
    private String bookTitle;

    @NotNull(message = "le nombre de copie n'est pas renseigné")
    @Min(value = 1, message = "vous devez disposer d'au moins un exemplaire du livre pour l'enregistrer")
    @ApiModelProperty(notes = "copies available for a book")
    private int copiesAvailable;

    @NotEmpty(message = "la catégorie du livre n'est pas renseigné")
    @ApiModelProperty(notes = "book category")
    private String BookCategoryLabel;

    public BookDTO() {
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

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    public String getBookCategoryLabel() {
        return BookCategoryLabel;
    }

    public void setBookCategoryLabel(String BookCategoryLabel) {
        this.BookCategoryLabel = BookCategoryLabel;
    }

    @Override
    public String toString() {
        return "BookDTO{" + "isbn=" + isbn + ", authorFirstname=" + author + ", bookTitle=" + bookTitle + ", copiesAvailable=" + copiesAvailable + '}';
    }

}
