package com.bigcity.services.interfaces;

import com.bigcity.entity.Role;
import java.util.List;


public interface IRoleService {

    /**
     * method to register a role
     *
     * @param role
     * @return role object saved
     * @throws Exception
     */
    Role register(String role) throws Exception;

    /**
     * method to modify a role
     *
     * @param role
     * @return role object modified
     * @throws Exception
     */
    Role edit(Role role) throws Exception;

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
     */
    Role getRole(Long id);

    /**
     * method to get a user role list by label
     *
     * @param label
     * @return role object list find by label
     */
    List<Role> getRoleByLabel(String label);

    /**
     * method to get the default user role
     *
     * @return default role for a user
     * @throws Exception
     */
    Role getDefaultRole() throws Exception;
}
