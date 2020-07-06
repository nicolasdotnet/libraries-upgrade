package com.bigcity.apiweb.services.interfaces;

import com.bigcity.apiweb.dto.UserDTO;
import com.bigcity.apiweb.dto.LoginDTO;
import com.bigcity.apiweb.entity.Role;
import com.bigcity.apiweb.entity.User;
import com.bigcity.apiweb.specifications.UserCriteria;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

public interface IUserService {

    /**
     * method to register a user by default
     *
     * @param userDTO
     * @return user object saved
     * @throws Exception
     */
    User registerByDefault(UserDTO userDTO) throws Exception;

    /**
     * method to register a membre user by default
     *
     * @param userDTO
     * @return user object saved
     * @throws Exception
     */
    User registerForMembre(UserDTO userDTO) throws Exception;

    /**
     * method to modify a user
     *
     * @param userDTO
     * @return user object modified
     * @throws Exception
     */
    User edit(UserDTO userDTO) throws Exception;

    /**
     * method to login a user
     *
     * @param loginDTO
     * @return user object
     * @throws Exception
     */
    User login(LoginDTO loginDTO) throws Exception;

    /**
     * method to get a profile for a user
     *
     * @param userId
     * @return user object find
     */
    User getUser(Long userId);

    /**
     * method to get all users
     *
     * @return the users list
     */
    List<User> getAllUsers();

    /**
     * method to get all users for a role
     *
     * @param UserCategory
     * @return the list users from Role label
     * @throws Exception
     */
    List<User> getUsersByRole(Role UserCategory) throws Exception;

    /**
     * method to get a user by his email
     *
     * @param email
     * @return the list users from Role label
     */
    Optional<User> getUserByEmail(String email);

    /**
     * method to get all users by email
     *
     * @param userName
     * @return the list users with the email
     */
    List<User> getAllUserByEmail(String userName);

    /**
     * method to update password
     *
     * @param passwordNew
     * @param email
     * @return user objet update
     * @throws Exception
     */
    User updatePassword(String passwordNew, String email) throws Exception;

    /**
     * method to get all really old users
     *
     * @param reallyOld
     * @return
     */
    List<User> getAllUsers(Specification<User> reallyOld);

    /**
     * method to get all users by criteria
     *
     * @param userCriteria
     * @param page
     * @param size
     * @return
     */
    Page<User> getAllUsersByCriteria(UserCriteria userCriteria, int page, int size);

}
