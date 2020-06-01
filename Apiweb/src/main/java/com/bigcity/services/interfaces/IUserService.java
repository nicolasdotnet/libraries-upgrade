package com.bigcity.services.interfaces;

import com.bigcity.dto.UserDTO;
import com.bigcity.entity.Role;
import com.bigcity.entity.User;
import java.util.List;
import java.util.Optional;
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
     * method to get a profile for a user
     *
     * @param userId
     * @return user object find
     * @throws Exception
     */
    User getUser(Long userId) throws Exception;

    /**
     * method to get all users
     *
     * @return the users list
     * @throws java.lang.Exception
     */
    List<UserDTO> getAllUsers() throws Exception;

    /**
     * method to get all users for a role
     *
     * @param UserCategory
     * @return the list users from Role label
     * @throws Exception
     */
    List<User> getUsersByRole(Role UserCategory) throws Exception;

    /**
     * method to get a user by his username
     *
     * @param userName
     * @return the list users from Role label
     * @throws Exception
     */
    Optional<User> getUserByUsername(String userName) throws Exception;

    /**
     * method to remove a user
     *
     * @param username
     * @throws Exception
     */
    void delete(String username) throws Exception;

    /**
     * method to get all users by username
     *
     * @param userName
     * @return the list users with the username
     */
    List<User> getAllUserByUsername(String userName);

    /**
     * method to update password
     *
     * @param passwordNew
     * @param username
     * @return user objet update
     * @throws Exception
     */
    User updatePassword(String passwordNew, String username) throws Exception;

    /**
     * method to get all really old users
     *
     * @param reallyOld
     * @return
     */
    List<User> getAllUsers(Specification<User> reallyOld);

}
