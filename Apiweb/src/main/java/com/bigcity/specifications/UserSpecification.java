/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.specifications;

import com.bigcity.entity.User;

import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author nicolasdotnet
 */
public class UserSpecification {

 public static Specification<User> isReallyOld() {     
     return (root, query, cb) -> {
         
         return cb.equal(root.get("userDate"),root.get("userDate"));};}

    
}



