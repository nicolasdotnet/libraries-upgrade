package com.bigcity.appweb.beans;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.stereotype.Component;

/**
 * @author nicolasdotnet
 *
 * Role is the category entity to which users can belong.
 *
 */
@Component
public class Role implements Serializable {

    private Long roleId;

    private String roleName;

    private Collection<User> users;

    public Role() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" + "roleName=" + roleName + '}';
    }
    
    
}
