/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nicolasdotnet
 */
@RestController
public class TestController {

    @GetMapping("/Test")
    public String test() {

        return "Un exemple de produit";
    }

}
