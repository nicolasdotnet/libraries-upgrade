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
public class BookCriteria {

    @ApiModelProperty(notes = "isbn number")
    private String isbn;

    @ApiModelProperty(notes = "book author")
    private String author;

    @ApiModelProperty(notes = "book title")
    private String bookTitle;

    @ApiModelProperty(notes = "book category")
    private String categoryName;

    public BookCriteria() {
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    
    
}
