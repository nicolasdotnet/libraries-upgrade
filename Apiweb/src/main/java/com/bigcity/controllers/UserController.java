package com.bigcity.controllers;

import com.bigcity.dto.UserDTO;
import com.bigcity.entity.User;
import com.bigcity.services.interfaces.IUserService;
import com.bigcity.specifications.UserSpecification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Api("API pour les opérations CRUD sur les utilisateurs.")
@RestController
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    private IUserService iUserService;

    @ApiOperation("Enregister un nouveau utilisateur")
    @PostMapping("/api/users")
    public ResponseEntity saveUser(@Valid @RequestBody UserDTO userDto) {

        log.debug("saveUser()");

        // TODO ajouter securité
        User userSave = null;

        try {
            userSave = iUserService.registerByDefault(userDto);
        } catch (Exception ex) {

            return ResponseEntity.badRequest().body(ex.getMessage());

        }

//code 201, ajouter l'URI 
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userSave.getUserId())
                .toUri();

        return ResponseEntity.created(location)
                .body(userSave);

    }

    @ApiOperation("Récupère un utilisateur grâce à son ID à condition que celui-ci soit inscrit !")
    @GetMapping("/api/user/{id}")
    public ResponseEntity showUser(@PathVariable("id") int id) {

        log.debug("showUser() id: {}", id);

        User userFind = null;

        try {
            userFind = iUserService.getUser(Long.valueOf(id));
        } catch (Exception ex) {

            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(userFind);

    }

    @ApiOperation("Récupère l'ensemble des utilidateurs de la base")
    @GetMapping("/api/users")
    public ResponseEntity showAllUsers() {

        log.debug("showAllUsers()");

        List<UserDTO> users = null;
        
        try {
            users = iUserService.getAllUsers();
        } catch (Exception ex) {

            return ResponseEntity.ok().body(ex.getMessage());
        }

        return ResponseEntity.ok(users);
    }

    @ApiOperation("Mettre à jour un utilisateur à partir de son ID présent dans la base")
    @PutMapping("/api/user/{id}")
    public ResponseEntity updateUser(@PathVariable("id") int id, @Valid @RequestBody UserDTO userDTO) {

        log.debug("updateUser()");

        User userNew = null;

        try {
            userNew = iUserService.edit(userDTO);
        } catch (Exception ex) {

            return ResponseEntity.badRequest().body(ex.getMessage());

        }

        return ResponseEntity.ok().body(userNew);

    }

    @ApiOperation("Test spécification : Récupère l'ensemble des utilidateurs de la base en fonction de leur ancienté")
    @GetMapping("/s")
    public List<User> gets() {

        return iUserService.getAllUsers(UserSpecification.isReallyOld());
    }

//    // delette user
//    @DeleteMapping("/User/{id}")
//    public String deleteUser(Principal principal, final RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {
//
//        log.debug("deleteUser()");
//
//        try {
//            iUserService.delete(principal.getName());
//        } catch (Exception e) {
//
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//
//            return "redirect:/user/account";
//        }
//
//        redirectAttributes.addFlashAttribute("delete", "Membre supprimé !");
//
//        new SecurityContextLogoutHandler().logout(request, null, null);
//
//        return "redirect:/confirmation";
//
//    }
}
