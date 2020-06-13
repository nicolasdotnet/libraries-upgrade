package com.bigcity.controllers;

import com.bigcity.dto.BookDTO;
import com.bigcity.dto.BookingDTO;
import com.bigcity.dto.UserDTO;
import com.bigcity.entity.User;
import com.bigcity.services.interfaces.IUserService;
import com.bigcity.specifications.UserCriteria;
import com.bigcity.specifications.UserSpecification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Api(tags = "API pour les opérations CRUD sur les utilisateurs par un bibliothécaire.")
@RestController
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    private IUserService iUserService;

    @ApiOperation("Enregister un nouveau utilisateur usagé")
    @ApiResponses(value = {
        @ApiResponse(code = SC_OK, message = "ok", response = BookDTO.class),
        @ApiResponse(code = SC_BAD_REQUEST, message = "erreur de saisie", response = BookDTO.class),
        @ApiResponse(code = SC_CONFLICT, message = "l'utilisateur existe déjà dans la base"),
        @ApiResponse(code = SC_UNAUTHORIZED, message = "une authentification est nécessaire")
    })
    @PostMapping("/api/user")
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserDTO userDto) throws Exception {

        log.debug("saveUser()");

        // TODO ajouter securité
        User userSave = iUserService.registerByDefault(userDto);

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
    @ApiResponses(value = {
        @ApiResponse(code = SC_OK, message = "ok", response = BookingDTO.class),
        @ApiResponse(code = SC_NOT_FOUND, message = "l'utilisateur n'existe pas dans la base"),
        @ApiResponse(code = SC_BAD_REQUEST, message = "erreur de saisie", response = BookingDTO.class),
        @ApiResponse(code = SC_UNAUTHORIZED, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/{id}")
    public ResponseEntity showUser(@PathVariable("id") int id) {

        log.debug("showUser() id: {}", id);

        return ResponseEntity.ok(iUserService.getUser(Long.valueOf(id)));

    }

    @ApiOperation("Mettre à jour un utilisateur à partir de son ID présent dans la base")
    @ApiResponses(value = {
        @ApiResponse(code = SC_OK, message = "ok", response = BookingDTO.class),
        @ApiResponse(code = SC_NOT_FOUND, message = "l'utilisateur n'existe pas dans la base"),
        @ApiResponse(code = SC_BAD_REQUEST, message = "erreur de saisie", response = BookingDTO.class),
        @ApiResponse(code = SC_UNAUTHORIZED, message = "une authentification est nécessaire")
    })
    @PutMapping("/api/user/{id}")
    public ResponseEntity updateUser(@PathVariable("id") int id, @Valid @RequestBody UserDTO userDTO) throws Exception {

        log.debug("updateUser()");

        return ResponseEntity.ok().body(iUserService.edit(userDTO));

    }

    @ApiOperation("Test spécification : Récupère l'ensemble des utilidateurs de la base en fonction de leur ancienté")
    @GetMapping("/s")
    public List<User> gets() {

        return iUserService.getAllUsers(UserSpecification.isReallyOld());
    }

    @ApiOperation("Récupère l'ensemble des utilisateurs de la base en fonction du prénom, du nom, de l'email et du role")
    @ApiResponses(value = {
        @ApiResponse(code = SC_OK, message = "ok", response = BookDTO.class),
        @ApiResponse(code = SC_BAD_REQUEST, message = "erreur de saisie", response = BookDTO.class),
        @ApiResponse(code = SC_UNAUTHORIZED, message = "une authentification est nécessaire")
    })
    @PostMapping("/api/users")
    public Page<User> showAllUsersByCriteria(@RequestBody UserCriteria userCriteria, int page, int size) throws Exception {

        log.debug("showAllUsersByCriteria", userCriteria);

        return iUserService.getAllUsersByCriteria(userCriteria, page, size);
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
