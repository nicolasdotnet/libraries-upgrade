package com.bigcity.apiweb.dao;

import com.bigcity.apiweb.entity.Role;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String userCategoryLabel);

}
