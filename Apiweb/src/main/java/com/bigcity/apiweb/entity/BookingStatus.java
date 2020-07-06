package com.bigcity.apiweb.entity;

/**
 *
 * @author nicolasdotnet
 * 
 * BookingStatus is the enum class to which booking can belong.
 * 
 */
public enum BookingStatus {
    
   ENCOURS("ENCOURS"), PROLONGE("PROLONGE"), TERMINE("TERMINE");

   private String value;

    private BookingStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
