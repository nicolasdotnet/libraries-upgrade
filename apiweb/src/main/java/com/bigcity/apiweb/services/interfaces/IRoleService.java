package com.bigcity.apiweb.services.interfaces;

import com.bigcity.apiweb.entity.Role;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import java.util.List;


public interface IRoleService {

    /**
     * method to register a role
     *
     * @param role
     * @return role object saved
     * @throws com.bigcity.apiweb.exceptions.EntityAlreadyExistsException
     */
    Role register(String role) throws EntityAlreadyExistsException;

    /**
     * method to modify a role
     *
     * @param role
     * @return role object modified
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    Role edit(Role role)throws EntityNotFoundException;

    /**
     * method to get all user role
     *
     * @return the user role list
     */
    List<Role> getAllRole();

    /**
     * method to get a user role
     *
     * @param id
     * @return role object find
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    Role getRole(Long id)throws EntityNotFoundException;

    /**
     * method to get the default user role
     *
     * @return default role for a user
     * @throws com.bigcity.apiweb.exceptions.EntityNotFoundException
     */
    Role getDefaultRole() throws EntityNotFoundException;
}
