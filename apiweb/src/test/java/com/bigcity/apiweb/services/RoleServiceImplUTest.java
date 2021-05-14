/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.services;

import com.bigcity.apiweb.dao.IRoleRepository;
import com.bigcity.apiweb.entity.Role;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author nicolasdotnet
 */
@ExtendWith(MockitoExtension.class)
public class RoleServiceImplUTest {

    @InjectMocks
    private RoleServiceImpl instance;

    @Mock
    private IRoleRepository iRoleRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of register method, of class RoleServiceImpl.
     */
    @Test
    public void testRegisterWhenTheRoleExist() throws Exception {
        System.out.println("register");

        Exception exception = assertThrows(EntityAlreadyExistsException.class, () -> {

            String roleName = "usager";

            Role role = new Role();
            Optional<Role> op = Optional.of(role);

            doReturn(op).when(iRoleRepository).findByRoleName(roleName);

            instance.register(roleName);
        });

        String expectedMessage = "La role existe déjà !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of register method, of class RoleServiceImpl.
     */
    @Test
    public void testRegister() throws Exception {
        System.out.println("register");

        String roleName = "fake";

        Role role = new Role();
        role.setRoleName("fake");

        Optional<Role> op = Optional.empty();
        doReturn(op).when(iRoleRepository).findByRoleName(roleName);
        doReturn(role).when(iRoleRepository).save(Mockito.any());

        Role r = instance.register(roleName);

        assertEquals("fake", r.getRoleName());

        Mockito.verify(iRoleRepository).save(Mockito.any());

    }

    /**
     * Test of Edit method, of class RoleServiceImpl.
     */
    @Test
    public void testEdit() throws Exception {

        System.out.println("edit");

        Role role = new Role();
        role.setRoleId(00L);
        role.setRoleName("fake");

        Optional<Role> op = Optional.of(role);
        doReturn(op).when(iRoleRepository).findById(role.getRoleId());
        doReturn(role).when(iRoleRepository).saveAndFlush(Mockito.any());

        Role r = instance.edit(role);

        assertEquals("fake", r.getRoleName());

        Mockito.verify(iRoleRepository).saveAndFlush(Mockito.any());

    }

    /**
     * Test of edit method, of class BookServiceImpl.
     */
    @Test
    public void testEditWhenRoleIsNotExit() throws Exception {
        System.out.println("edit");

        Role role = new Role();
        role.setRoleId(00L);
        role.setRoleName("fake");

        Optional<Role> op = Optional.empty();

        doReturn(op).when(iRoleRepository).findById(role.getRoleId());

        try {

            instance.edit(role);

        } catch (Exception e) {

            assertTrue(e instanceof EntityNotFoundException);
            assertEquals(e.getMessage(), "La role n'existe pas !");

        }

    }

    /**
     * Test of Edit method, of class RoleServiceImpl.
     */
    @Test
    public void testgetAllRole() throws Exception {

        System.out.println("getAllRole()");

        Role role = new Role();
        role.setRoleId(00L);
        role.setRoleName("fake");

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        doReturn(roles).when(iRoleRepository).findAll();

        List<Role> r = instance.getAllRole();

        assertEquals(roles.size(), r.size());

        Mockito.verify(iRoleRepository).findAll();

    }

    /**
     * Test of getRole method, of class RoleServiceImpl.
     */
    @Test
    public void testGetRoleWhenRoleIsNotExit() throws Exception {
        System.out.println("getRole");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {

            Long id = 1L;
            Optional<Role> op = Optional.empty();

            doReturn(op).when(iRoleRepository).findById(id);

            instance.getRole(id);
        });

        String expectedMessage = "le role n'existe pas !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of getRole method, of class RoleServiceImpl.
     */
    @Test
    public void testGetRole() throws Exception {
        System.out.println("getRole");

        Long id = 1L;

        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("fake");

        Optional<Role> op = Optional.of(role);

        doReturn(op).when(iRoleRepository).findById(id);

        Role r = instance.getRole(id);

        assertEquals(role.getRoleName(), r.getRoleName());

        Mockito.verify(iRoleRepository).findById(id);

    }

    /**
     * Test of getDefaultRole method, of class RoleServiceImpl.
     */
    @Test
    public void testGetDefaultRoleWhenItNotExist() throws Exception {
        System.out.println("getDefaultRole");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {

            Optional<Role> op = Optional.empty();

            doReturn(op).when(iRoleRepository).findById(1L);

            instance.getDefaultRole();
        });

        String expectedMessage = "La role par défault n'existe pas !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of getDefaultRole method, of class RoleServiceImpl.
     */
    @Test
    public void testGetDefaultRole() throws Exception {
        System.out.println("getDefaultRole");

        Long id = 1L;

        Role role = new Role();
        role.setRoleId(1L);
        role.setRoleName("fake");

        Optional<Role> op = Optional.of(role);

        doReturn(op).when(iRoleRepository).findById(id);

        Role r = instance.getDefaultRole();

        assertEquals(role.getRoleName(), r.getRoleName());

        Mockito.verify(iRoleRepository).findById(id);

    }

}
