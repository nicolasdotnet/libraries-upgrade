package com.bigcity.appweb.beans;

/**
 *
 * @author nicolasdotnet
 * 
 * BookingStatus is the enum class to which booking can belong.
 * 
 */
public enum BookingStatus {
    
   ENCOURS("ENCOURS"), PROLONGE("PROLONGE"), TERMINE("TERMINE"), RESERVE("RESERVE");

   private String value;

    private BookingStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
}
