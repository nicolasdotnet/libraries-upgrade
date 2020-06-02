package com.bigcity.dao;

import com.bigcity.entity.Role;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String userCategoryLabel);

    List<Role> findByRoleNameContainingIgnoreCase(String label);

}
