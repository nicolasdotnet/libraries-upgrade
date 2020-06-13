package com.bigcity.dao;

import com.bigcity.entity.Role;
import com.bigcity.entity.User;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IUserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {

    List<User> findAllByRole(Role roleName);

    Optional<User> findByEmail(String email);

    List<User> findAllByEmail(String email);

}