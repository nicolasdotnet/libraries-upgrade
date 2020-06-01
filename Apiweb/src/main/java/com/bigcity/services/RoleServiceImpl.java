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
    private RoleRepository userCategoryRepository;

    @Override
    public Role register(String category) throws Exception {

        if (userCategoryRepository.findByRoleNameIgnoreCase(category) != null) {

            log.error("Le role existe déjà !");

            throw new Exception("La catégorie "+ category+" existe déjà !");

        }

        Role userCategory = new Role();

        userCategory.setRoleName(category);
        userCategory.setRoleDate(new Date());

        return userCategoryRepository.save(userCategory);
    }

    @Override
    public Role edit(Role userCategory) throws Exception {

        Optional<Role> categoryFind = userCategoryRepository.findById(userCategory.getRoleId());

        if (!categoryFind.isPresent()) {

            log.error("Modification Impossible ! le role " + userCategory.getRoleId()
                    + " n'existe pas dans la base.");

            throw new Exception("La catégorie " + userCategory.getRoleId()
                    + " n'existe pas !");

        }

        return userCategoryRepository.saveAndFlush(userCategory);
    }

    @Override
    public List<Role> getAllUserCategory() {

        return userCategoryRepository.findAll();
    }

    @Override
    public Role getUserCategory(Long id) throws Exception {

        Optional<Role> categoryFind = userCategoryRepository.findById(id);

        if (!categoryFind.isPresent()) {

            log.error("Le role " + id
                    + " n'existe pas dans la base.");

            throw new Exception("La catégorie "+ id+" n'existe pas !");

        }
        return categoryFind.get();
    }

    @Override
    public Role getDefaultUserCategory() throws Exception {

        Optional<Role> defaultCategory = userCategoryRepository.findById(1L);

        if (!defaultCategory.isPresent()) {

            log.error("Le role par défault n'existe pas dans la base.");

            throw new Exception("La catégorie par défault n'existe pas !");

        }

        return defaultCategory.get();
    }

    @Override
    public List<Role> getUserCategoryByLabel(String label) {

        return userCategoryRepository.findByRoleNameContainingIgnoreCase(label);
    }

}
