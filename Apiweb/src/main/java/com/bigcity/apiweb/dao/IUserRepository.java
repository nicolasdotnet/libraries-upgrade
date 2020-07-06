package com.bigcity.apiweb.dao;

import com.bigcity.apiweb.entity.Role;
import com.bigcity.apiweb.entity.User;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {

    List<User> findAllByRole(Role roleName);

    Optional<User> findByEmail(String email);

    List<User> findAllByEmail(String email);

}
