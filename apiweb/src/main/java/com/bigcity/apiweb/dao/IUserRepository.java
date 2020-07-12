package com.bigcity.apiweb.dao;

import com.bigcity.apiweb.entity.User;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);

}
