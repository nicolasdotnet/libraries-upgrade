package com.bigcity.services;

import com.bigcity.dao.RoleRepository;
import com.bigcity.entity.Role;
import com.bigcity.services.interfaces.IRoleService;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    private static final Logger log = LogManager.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;
    

    @Override
    public Role register(String roleName) throws Exception {

        Optional<Role> roleFind = roleRepository.findByRoleName(roleName);

        if (roleFind.isPresent()) {

            log.error("Le role existe déjà !");

            throw new Exception("La role existe déjà !");

        }

        Role role = new Role();

        role.setRoleName(roleName);

        return roleRepository.save(role);
    }

    @Override
    public Role edit(Role role) throws Exception {

        Optional<Role> roleFind = roleRepository.findById(role.getRoleId());

        if (!roleFind.isPresent()) {

            log.error("Modification Impossible ! le role n'existe pas dans la base.");

            throw new Exception("La role n'existe pas !");

        }

        return roleRepository.saveAndFlush(role);
    }

    @Override
    public List<Role> getAllRole() {

        return roleRepository.findAll();
    }

    @Override
    public Role getRole(Long id) throws Exception {

        Optional<Role> roleFind = roleRepository.findById(id);

        if (!roleFind.isPresent()) {

            log.error("Le role " + id
                    + " n'existe pas dans la base.");

            throw new Exception("La role n'existe pas !");

        }
        return roleFind.get();
    }

    @Override
    public Role getDefaultRole() throws Exception {

        Optional<Role> defaultCategory = roleRepository.findById(1L);

        if (!defaultCategory.isPresent()) {

            log.error("Le role par défault n'existe pas dans la base.");

            throw new Exception("La role par défault n'existe pas !");

        }

        return defaultCategory.get();
    }

    @Override
    public List<Role> getRoleByLabel(String roleName
    ) {

        return roleRepository.findByRoleNameContainingIgnoreCase(roleName);
    }

}
