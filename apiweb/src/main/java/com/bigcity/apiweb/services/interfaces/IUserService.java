package com.bigcity.apiweb.services.interfaces;

import com.bigcity.apiweb.dto.UserDTO;
import com.bigcity.apiweb.dto.LoginDTO;
import com.bigcity.apiweb.entity.User;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.specifications.UserCriteria;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface IUserService {

    /**
     * method to register a user by default
     *
     * @param userDTO
     * @return user object saved
     * @throws com.bigcity.apiweb.exceptions.EntityAlreadyExistsException
     */
    User registerByDefault(UserDTO userDTO) throws EntityAlreadyExistsException, EntityNotFoundException;

    /**
     * method to register a membre user by default
     *
     * @param userDTO
     * @return user object saved
     * @throws com.bigcity.apiweb.exceptions.EntityAlreadyExistsException
     */
    User registerForMembre(UserDTO userDTO) throws EntityAlreadyExistsException, EntityNotFoundException;

    /**
     * method to modify a user
     *
     * @param userDTO
     * @return user object modified
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    User edit(UserDTO userDTO) throws EntityNotFoundException;

    /**
     * method to login a user
     *
     * @param loginDTO
     * @return user object
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    User login(LoginDTO loginDTO) throws EntityNotFoundException;

    /**
     * method to get a profile for a user
     *
     * @param userId
     * @return user object find
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    User getUser(Long userId) throws EntityNotFoundException ;

    /**
     * method to get a user by his email
     *
     * @param email
     * @return the list users from Role label
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    Optional<User> getUserByEmail(String email) throws EntityNotFoundException;

    /**
     * method to update password
     *
     * @param passwordNew
     * @param email
     * @return user objet update
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    User updatePassword(String passwordNew, String email) throws EntityNotFoundException;

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
