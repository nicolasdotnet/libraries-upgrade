/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.batch.bean;

/**
 *
 * @author nicolasdotnet
 */
public enum ReservationStatus {

    ENCOURS("ENCOURS"), ATTENTE("ATTENTE"), TERMINE("TERMINE");

    private String value;

    private ReservationStatus (String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
