package com.bigcity.dao;

import com.bigcity.entity.Role;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String userCategoryLabel);

    Role findByRoleNameIgnoreCase(String category);

    List<Role> findByRoleNameContainingIgnoreCase(String label);

}
