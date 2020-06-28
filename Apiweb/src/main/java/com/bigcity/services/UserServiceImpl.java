package com.bigcity.services;

import com.bigcity.dto.UserDTO;
import com.bigcity.entity.Role;
import com.bigcity.entity.User;
import com.bigcity.exceptions.EntityAlreadyExistsException;
import com.bigcity.exceptions.EntityNotFoundException;
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
import com.bigcity.dao.IUserRepository;
import com.bigcity.dto.LoginDTO;
import com.bigcity.specifications.UserCriteria;
import com.bigcity.specifications.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

            throw new EntityNotFoundException("Utilisateur n'existe pas !");

        }

        userFind.get().setFirstname(userDTO.getFirstname());
        userFind.get().setLastname(userDTO.getLastname());
        userFind.get().setEmail(userDTO.getEmail());
        return userRepository.saveAndFlush(userFind.get());
    }

    @Override
    public User getUser(Long id) {

        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRole(Role role) throws Exception {

        Role roleFind = iRoleService.getRole(role.getRoleId());

        if (roleFind == null) {

            log.error("le role n'existe pas dans la base.");

            throw new EntityNotFoundException("le role n'existe pas !");
        }

        return userRepository.findAllByRole(roleFind);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {

        return userRepository.findByEmail(email);
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

            throw new EntityNotFoundException("Utilisateur n'existe pas !");

        }

        userFind.get().setPassword(bCryptPasswordEncoder.encode(passwordNew));

        return userRepository.saveAndFlush(userFind.get());

    }

    @Override
    public List<User> getAllUsers(Specification<User> reallyOld) {

        return userRepository.findAll(reallyOld);
    }

    @Override
    public Page<User> getAllUsersByCriteria(UserCriteria userCriteria, int page, int size) {

        userCriteria.setEmail("".equals(userCriteria.getEmail()) ? null : userCriteria.getEmail());
        userCriteria.setFirstname("".equals(userCriteria.getFirstname()) ? null : userCriteria.getFirstname());
        userCriteria.setLastname("".equals(userCriteria.getLastname()) ? null : userCriteria.getLastname());
        userCriteria.setRole("".equals(userCriteria.getRole()) ? null : userCriteria.getRole());

        UserSpecification userSpecification = new UserSpecification(userCriteria);

        return userRepository.findAll(userSpecification, PageRequest.of(page, size));
    }

    public User dtoToEntity(UserDTO userDTO) {

        User user = new User();

        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
//        user.setRole(userDTO.getRole());

        return user;

    }

    @Override
    public User login(LoginDTO loginDTO) throws Exception {

        Optional<User> userFind = userRepository.findByEmail(loginDTO.getEmail());

        if (!userFind.isPresent()) {

            log.error("Connection impossible ! l'utilisateur n'existe pas dans la base.");

            throw new EntityNotFoundException("Utilisateur n'existe pas !");

        }
        
        boolean result = passwordEncoder.matches(loginDTO.getPassword(), userFind.get().getPassword());

        if (!result) {

            log.error("MOT DE PASSE ! ! l'utilisateur n'existe pas dans la base.");

            throw new EntityNotFoundException("MOT DE PASSE !");

        }

        return userFind.get();

    }

}
