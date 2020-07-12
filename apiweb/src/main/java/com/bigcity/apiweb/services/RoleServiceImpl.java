package com.bigcity.apiweb.services;

import com.bigcity.apiweb.entity.Role;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.services.interfaces.IRoleService;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bigcity.apiweb.dao.IRoleRepository;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    private static final Logger log = LogManager.getLogger(RoleServiceImpl.class);

    @Autowired
    private IRoleRepository roleRepository;
    

    @Override
    public Role register(String roleName) throws Exception {

        Optional<Role> roleFind = roleRepository.findByRoleName(roleName);

        if (roleFind.isPresent()) {

            log.error("Le role existe déjà !");

            throw new EntityAlreadyExistsException("La role existe déjà !");

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

            throw new EntityNotFoundException("La role n'existe pas !");

        }

        return roleRepository.saveAndFlush(role);
    }

    @Override
    public List<Role> getAllRole() {

        return roleRepository.findAll();
    }

    @Override
    public Role getRole(Long id){
        
        return roleRepository.findById(id).get();
    }

    @Override
    public Role getDefaultRole() throws Exception {

        Optional<Role> defaultCategory = roleRepository.findById(1L);

        if (!defaultCategory.isPresent()) {

            log.error("Le role par défault n'existe pas dans la base.");

            throw new EntityNotFoundException("La role par défault n'existe pas !");

        }

        return defaultCategory.get();
    }

}
