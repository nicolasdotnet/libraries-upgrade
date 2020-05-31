package com.bigcity.services;

import com.bigcity.exceptions.UserNoFindException;
import com.bigcity.dao.UserRepository;
import com.bigcity.dto.UserDTO;
import com.bigcity.entity.Role;
import com.bigcity.entity.User;
import com.bigcity.exceptions.UsersNoFindException;
import static com.bigcity.security.EncrytedPasswordUtils.encrytePassword;
import com.bigcity.services.interfaces.IRoleService;
import com.bigcity.services.interfaces.IUserService;
import java.util.ArrayList;
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

        Optional<User> userFind = userRepository.findByUsername(userDTO.getUsername());

        if (userFind.isPresent()) {

            log.error("Utilisateur existe déjà !");

            throw new Exception("Utilisateur existe déjà !");

        }

        Role role = iRoleService.getUserCategory(2L);

        User user = dtoToEntity(userDTO);
      
        user.setRole(role);
        user.setUserDate(new Date());

        return userRepository.save(user);

    }

    @Override
    public User registerForMembre(UserDTO userDTO) throws Exception {

        Optional<User> userFind = userRepository.findByUsername(userDTO.getUsername());

        if (userFind.isPresent()) {

            log.error("Utilisateur existe déjà !");

            throw new Exception("Utilisateur existe déjà !");

        }

        Role role = iRoleService.getUserCategory(1L);
        
        
        User user = dtoToEntity(userDTO);

        user.setRole(role);
        user.setUserDate(new Date());

        return userRepository.save(user);

    }

    @Override
    public User edit(UserDTO userDTO) throws Exception {

        Optional<User> userFind = userRepository.findByUsername(userDTO.getUsername());

        if (!userFind.isPresent()) {

            log.error("Modification Impossible ! l'utilisateur n'existe pas dans la base.");

            throw new Exception("Utilisateur n'existe pas !");

        }

        userFind.get().setFirstname(userDTO.getFirstname());
        userFind.get().setLastname(userDTO.getLastname());
        userFind.get().setEmail(userDTO.getEmail());
        return userRepository.saveAndFlush(userFind.get());
    }

    @Override
    public UserDTO getUser(Long id) throws UserNoFindException {

        Optional<User> userFind = userRepository.findById(id);

        if (!userFind.isPresent()) {

            log.error("L'utilisateur  n'existe pas dans la base.");

            throw new UserNoFindException("Utilisateur  n'existe pas !");

        }
        

        UserDTO userDto = entityToDto(userFind.get());

        return userDto;
    }

    @Override
    public List<UserDTO> getAllUsers() throws Exception{

        List<User> users = userRepository.findAll();
        
        List<UserDTO> userDTOs = new ArrayList<>();

        if (users.isEmpty()) {

            throw new UsersNoFindException("Il n'y a pas d'utilisateurs dans la base.");
        }
        
        for (User user : users) {
            
            UserDTO n = entityToDto(user);
            
            userDTOs.add(n);
            
        }

        return userDTOs;
    }

    @Override
    public List<User> getUsersByRole(Role userCategory) throws Exception {

        iRoleService.getUserCategory(userCategory.getRoleId());

        return userRepository.findByRole(userCategory);
    }

    @Override
    public Optional<User> getUserByUsername(String username) throws Exception {

        Optional<User> userFind = userRepository.findByUsername(username);

        if (!userFind.isPresent()) {

            log.error("L'utilisateur n'existe pas dans la base.");

            throw new Exception("Utilisateur n'existe pas !");

        }

        return userFind;
    }

    @Override
    public void delete(String username) throws Exception {

        Optional<User> userFind = userRepository.findByUsername(username);

        if (!userFind.isPresent()) {

            log.error("Modification Impossible ! l'utilisateur  n'existe pas dans la base.");

            throw new Exception("Utilisateur n'existe pas !");

        }

        userRepository.deleteById(userFind.get().getUserId());
    }

    @Override
    public List<User> getAllUserByUsername(String userName) {
        return userRepository.findAllByUsernameContainingIgnoreCase(userName);
    }

    @Override
    public User updatePassword(String passwordNew, String username) throws Exception {

        Optional<User> userFind = userRepository.findByUsername(username);

        if (!userFind.isPresent()) {

            log.error("Modification Impossible ! l'utilisateur n'existe pas dans la base.");

            throw new Exception("Utilisateur n'existe pas !");

        }

        userFind.get().setPassword(encrytePassword(passwordNew));
        return userRepository.saveAndFlush(userFind.get());

    }

    @Override
    public List<User> getAllUsers(Specification<User> reallyOld) {

        return userRepository.findAll(reallyOld);
    }

    public UserDTO entityToDto(User user) {

        UserDTO userDTO = new UserDTO();

        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
//        userDTO.setRole(user.getRole());

        return userDTO;

    }

    public User dtoToEntity(UserDTO userDTO) {

        User user = new User();

        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
//        user.setRole(userDTO.getRole());

        return user;

    }

}
