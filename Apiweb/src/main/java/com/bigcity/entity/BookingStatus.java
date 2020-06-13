package com.bigcity.entity;

/**
 *
 * @author nicolasdotnet
 * 
 * BookingStatus is the enum class to which booking can belong.
 * 
 */
public enum BookingStatus {
    
   ENCOURS, PROLONGE, TERMINE;

    private BookingStatus() {
    }
   

    private String status;

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
}
