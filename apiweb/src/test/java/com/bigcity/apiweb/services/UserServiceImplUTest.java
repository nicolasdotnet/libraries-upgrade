/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigcity.apiweb.services;

import com.bigcity.apiweb.dao.IUserRepository;
import com.bigcity.apiweb.dto.LoginDTO;
import com.bigcity.apiweb.dto.UserDTO;
import com.bigcity.apiweb.entity.Role;
import com.bigcity.apiweb.entity.User;
import com.bigcity.apiweb.exceptions.EntityAlreadyExistsException;
import com.bigcity.apiweb.exceptions.EntityNotFoundException;
import com.bigcity.apiweb.services.interfaces.IRoleService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author nicolasdotnet
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplUTest {

    @InjectMocks
    private UserServiceImpl instance;

    @Mock
    private IUserRepository iUserRepository;

    @Mock
    private IRoleService iRoleService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of getUser method, of class UserServiceImpl.
     */
    @Test
    public void testRegisterByDefaultWhenUserAlreadyExist() throws Exception {
        System.out.println("getRegisterByDefault");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("fake@mail.com");

        User user = new User();
        Optional<User> op = Optional.of(user);

        doReturn(op).when(iUserRepository).findByEmail("fake@mail.com");

        try {

            instance.registerByDefault(userDTO);

        } catch (Exception e) {

            assertTrue(e instanceof EntityAlreadyExistsException);

            assertEquals(e.getMessage(), "Utilisateur existe déjà !");

        }

    }

    /**
     * Test of registerByDefault method, of class UserServiceImpl.
     */
    @Test
    public void testRegisterByDefault() throws Exception {
        System.out.println("getRegisterByDefault");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("fake@mail.com");
        userDTO.setFirstname("fake");
        userDTO.setLastname("fkae");
        userDTO.setPassword("123");

        User user = new User();
        user.setFirstname("fake");
        Optional<User> op = Optional.empty();

        doReturn(op).when(iUserRepository).findByEmail("fake@mail.com");
        doReturn(new Role()).when(iRoleService).getRole(2L);

        doReturn("123").when(bCryptPasswordEncoder).encode("123");

        doReturn(user).when(iUserRepository).save(Mockito.any());

        User r = instance.registerByDefault(userDTO);

        assertEquals("fake", r.getFirstname());

        Mockito.verify(iUserRepository).save(Mockito.any());

    }

    /**
     * Test of registerForMembre method, of class UserServiceImpl.
     */
    @Test
    public void testRegisterForMembreWhenUserAlreadyExist() throws Exception {
        System.out.println("getRegisterForMembre");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("fake@mail.com");

        User user = new User();
        Optional<User> op = Optional.of(user);

        doReturn(op).when(iUserRepository).findByEmail("fake@mail.com");

        try {

            instance.registerForMembre(userDTO);

        } catch (Exception e) {

            assertTrue(e instanceof EntityAlreadyExistsException);

            assertEquals(e.getMessage(), "Utilisateur existe déjà !");

        }

    }

    /**
     * Test of registerForMembre method, of class UserServiceImpl.
     */
    @Test
    public void testRegisterForMembre() throws Exception {
        System.out.println("getRegisterByDefault");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("fake@mail.com");
        userDTO.setFirstname("fake");
        userDTO.setLastname("fkae");
        userDTO.setPassword("123");

        User user = new User();
        user.setFirstname("fake");
        Optional<User> op = Optional.empty();

        doReturn(op).when(iUserRepository).findByEmail("fake@mail.com");
        doReturn(new Role()).when(iRoleService).getRole(1L);

        doReturn("123").when(bCryptPasswordEncoder).encode("123");

        doReturn(user).when(iUserRepository).save(Mockito.any());

        User r = instance.registerForMembre(userDTO);

        assertEquals("fake", r.getFirstname());

        Mockito.verify(iUserRepository).save(Mockito.any());

    }

    /**
     * Test of edit method, of class UserServiceImpl.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("Edit");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("fake@mail.com");
        userDTO.setFirstname("fake");
        userDTO.setLastname("fkae");
        userDTO.setPassword("123");

        User user = new User();
        user.setEmail("fake@mail.com");
        user.setFirstname("fake");
        user.setLastname("fkae");
        user.setPassword("123");
        Optional<User> op = Optional.of(user);

        doReturn(op).when(iUserRepository).findByEmail("fake@mail.com");

        doReturn(user).when(iUserRepository).saveAndFlush(Mockito.any());

        User r = instance.edit(userDTO);

        assertEquals("fake", r.getFirstname());

        Mockito.verify(iUserRepository).saveAndFlush(Mockito.any());

    }

    /**
     * Test of edit method, of class UserServiceImpl.
     */
    @Test
    public void testEditWhenUserIsNotExit() throws Exception {

        System.out.println("Edit");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("fake@mail.com");

        User user = new User();
        Optional<User> op = Optional.empty();

        doReturn(op).when(iUserRepository).findByEmail("fake@mail.com");

        try {

            instance.edit(userDTO);

        } catch (Exception e) {

            assertTrue(e instanceof EntityNotFoundException);

            assertEquals(e.getMessage(), "Utilisateur n'existe pas !");

        }

    }

    /**
     * Test of getUser method, of class UserServiceImpl.
     */
    @Test
    public void testGetUserWhenUserIsNotExit() throws Exception {
        System.out.println("getUser");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {

            Long id = 1L;

            Optional<User> op = Optional.empty();

            doReturn(op).when(iUserRepository).findById(id);

            instance.getUser(id);
        });

        String expectedMessage = "l'utilisateur n'existe pas !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of getUser method, of class UserServiceImpl.
     */
    @Test
    public void testGetUser() throws Exception {
        System.out.println("getUser");

        Long id = 1L;

        User user = new User();
        user.setFirstname("fake");
        Optional<User> op = Optional.of(user);

        doReturn(op).when(iUserRepository).findById(id);

        User r = instance.getUser(id);
        assertEquals(user.getFirstname(), r.getFirstname());

        Mockito.verify(iUserRepository).findById(id);
    }

    /**
     * Test of getUserByEmail method, of class UserServiceImpl.
     */
    @Test
    public void testGetUserByEmail() throws Exception {
        System.out.println("getUserByEmail");

        String email = "fake@mail.com";

        User user = new User();
        user.setFirstname("fake");
        Optional<User> op = Optional.of(user);

        doReturn(op).when(iUserRepository).findByEmail(email);

        Optional<User> r = instance.getUserByEmail(email);
        assertEquals(user.getFirstname(), r.get().getFirstname());

        Mockito.verify(iUserRepository).findByEmail(email);

    }

    /**
     * Test of getUserByEmail method, of class UserServiceImpl.
     */
    @Test
    public void testGetUserByEmailWhenUserIsNotExit() throws Exception {
        System.out.println("getUserByEmail");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {

            String email = "fake@mail.com";

            Optional<User> op = Optional.empty();

            doReturn(op).when(iUserRepository).findByEmail(email);

            instance.getUserByEmail(email);
        });

        String expectedMessage = "l'utilisateur n'existe pas !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of updatePassword method, of class UserServiceImpl.
     */
    @Test
    public void testUpdatePasswordWhenUserIsNotExit() throws Exception {
        System.out.println("updatePassword");

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {

            String passwordNew = "";
            String email = "fake@mail.com";

            Optional<User> op = Optional.empty();

            doReturn(op).when(iUserRepository).findByEmail(email);

            instance.updatePassword(passwordNew, email);
        });

        String expectedMessage = "Utilisateur n'existe pas !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of updatePassword method, of class UserServiceImpl.
     */
    @Test
    public void testUpdatePassword() throws Exception {
        System.out.println("updatePassword");

        String passwordNew = "123";
        String email = "fake@mail.com";

        User user = new User();
        user.setFirstname("fake");
        Optional<User> op = Optional.of(user);

        doReturn(op).when(iUserRepository).findByEmail(email);
        doReturn("123").when(bCryptPasswordEncoder).encode("123");

        doReturn(user).when(iUserRepository).saveAndFlush(op.get());

        User r = instance.updatePassword(passwordNew, email);
        assertEquals(user.getFirstname(), r.getFirstname());

    }

    /**
     * Test of login method, of class UserServiceImpl.
     */
    @Test
    public void testLoginWhenUserIsNotExit() throws Exception {

        System.out.println("login");

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword("123");
        loginDTO.setEmail("fake@mail.com");

        User user = new User();
        Optional<User> op = Optional.empty();

        doReturn(op).when(iUserRepository).findByEmail("fake@mail.com");

        try {

            instance.login(loginDTO);

        } catch (Exception e) {

            assertTrue(e instanceof EntityNotFoundException);
            assertEquals(e.getMessage(), "Utilisateur n'existe pas !");

        }

    }

    /**
     * Test of login method, of class UserServiceImpl.
     */
    @Test
    public void testLoginWhenErrorMatchPassword() throws Exception {

        System.out.println("login");

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword("123");
        loginDTO.setEmail("fake@mail.com");

        User user = new User();
        user.setEmail("fake@mail.com");
        user.setPassword("123");
        Optional<User> op = Optional.of(user);

        doReturn(op).when(iUserRepository).findByEmail("fake@mail.com");
        doReturn(Boolean.FALSE).when(passwordEncoder).matches(loginDTO.getPassword(), op.get().getPassword());

        try {

            instance.login(loginDTO);

        } catch (Exception e) {

            assertTrue(e instanceof EntityNotFoundException);
            assertEquals(e.getMessage(), "MOT DE PASSE !");

        }

    }
    
    /**
     * Test of login method, of class UserServiceImpl.
     */
    @Test
    public void testLogin() throws Exception{
        
        System.out.println("login");
        
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword("123");
        loginDTO.setEmail("fake@mail.com");

        User user = new User();
        user.setEmail("fake@mail.com");
        user.setPassword("123");
        Optional<User> op = Optional.of(user);

        doReturn(op).when(iUserRepository).findByEmail("fake@mail.com");
        doReturn(Boolean.TRUE).when(passwordEncoder).matches(loginDTO.getPassword(), op.get().getPassword());
        
        User r = instance.login(loginDTO);
        assertEquals(loginDTO.getEmail(), r.getEmail());
        
    }

    /**
     * Test of dtoToEntity method, of class UserServiceImpl.
     */
    @Test
    public void testDtoToEntity() {
        System.out.println("dtoToEntity");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("fake@mail.com");
        userDTO.setFirstname("fake");
        userDTO.setLastname("fkae");
        userDTO.setPassword("123");

        User expResult = new User();
        expResult.setFirstname("fake");
        expResult.setLastname("fkae");
        expResult.setPassword("123");

        User result = instance.dtoToEntity(userDTO);
        assertEquals(expResult.getFirstname(), result.getFirstname());

    }
}
