package com.bigcity.services;

import com.bigcity.dao.UserRepository;
import com.bigcity.dto.UserDTO;
import com.bigcity.entity.Role;
import com.bigcity.entity.User;
import com.bigcity.exceptions.EntityAlreadyExistsException;
import com.bigcity.exceptions.EntityNoFoundException;
import static com.bigcity.security.EncrytedPasswordUtils.encrytePassword;
import com.bigcity.services.interfaces.IRoleService;
import com.bigcity.services.interfaces.IUserService;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IRoleService iRoleService;

    @Override
    public User registerByDefault(UserDTO userDTO) throws Exception {

        Optional<User> userFind = userRepository.findByEmail(userDTO.getEmail());

        if (userFind.isPresent()) {

            log.error("Utilisateur existe déjà !");

            throw new EntityAlreadyExistsException("Utilisateur existe déjà !");

        }

        Role role = iRoleService.getRole(2L);

        User user = dtoToEntity(userDTO);

        user.setRole(role);
        user.setUserDate(new Date());

        return userRepository.save(user);

    }

    @Override
    public User registerForMembre(UserDTO userDTO) throws Exception {

        Optional<User> userFind = userRepository.findByEmail(userDTO.getEmail());

        if (userFind.isPresent()) {

            log.error("Utilisateur existe déjà !");

            throw new EntityAlreadyExistsException("Utilisateur existe déjà !");

        }

        Role role = iRoleService.getRole(1L);

        User user = dtoToEntity(userDTO);

        user.setRole(role);
        user.setUserDate(new Date());

        return userRepository.save(user);

    }

    @Override
    public User edit(UserDTO userDTO) throws Exception {

        Optional<User> userFind = userRepository.findByEmail(userDTO.getEmail());

        if (!userFind.isPresent()) {

            log.error("Modification Impossible ! l'utilisateur n'existe pas dans la base.");

            throw new EntityNoFoundException("Utilisateur n'existe pas !");

        }

        userFind.get().setFirstname(userDTO.getFirstname());
        userFind.get().setLastname(userDTO.getLastname());
        userFind.get().setEmail(userDTO.getEmail());
        return userRepository.saveAndFlush(userFind.get());
    }

    @Override
    public User getUser(Long id){

        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRole(Role role) throws Exception {

        Role roleFind = iRoleService.getRole(role.getRoleId());
        
        if (roleFind==null) {
            
            log.error("le role n'existe pas dans la base.");

            throw new EntityNoFoundException("le role n'existe pas !");
        }

        return userRepository.findAllByRole(roleFind);
    }

    @Override
    public Optional<User> getUserByEmail(String email){

        return userRepository.findByEmail(email);
    }

    @Override
    public void delete(String email) throws Exception {

        Optional<User> userFind = userRepository.findByEmail(email);

        if (!userFind.isPresent()) {

            log.error("Modification Impossible ! l'utilisateur  n'existe pas dans la base.");

            throw new EntityNoFoundException("Utilisateur n'existe pas !");

        }

        userRepository.deleteById(userFind.get().getUserId());
    }

    @Override
    public List<User> getAllUserByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }

    @Override
    public User updatePassword(String passwordNew, String email) throws Exception {

        Optional<User> userFind = userRepository.findByEmail(email);

        if (!userFind.isPresent()) {

            log.error("Modification Impossible ! l'utilisateur n'existe pas dans la base.");

            throw new EntityNoFoundException("Utilisateur n'existe pas !");

        }

        userFind.get().setPassword(encrytePassword(passwordNew));

        return userRepository.saveAndFlush(userFind.get());

    }

    @Override
    public List<User> getAllUsers(Specification<User> reallyOld) {

        return userRepository.findAll(reallyOld);
    }

    public User dtoToEntity(UserDTO userDTO) {

        User user = new User();

        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
//        user.setRole(userDTO.getRole());

        return user;

    }

}
