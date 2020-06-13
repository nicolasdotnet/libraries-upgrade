package com.bigcity.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author nicolasdotnet
 */
public class BookCategoryDTO {

    @NotEmpty(message = "le nom de la catégorie n'est pas renseigné")
    @Size(min = 3, max = 10, message = "le nom de la catégorie trop long ou trop court")
    @ApiModelProperty(notes = "label for a category")
    private String label;

    public BookCategoryDTO() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "BookCategoryDTO{" + "label=" + label + '}';
    }
    
    

}
