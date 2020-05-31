package com.bigcity.dao;

import com.bigcity.entity.Role;
import com.bigcity.entity.User;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    List<User> findAllByUsernameContainingIgnoreCase(String username);

    List<User> findByRole(Role userCategory);

}
