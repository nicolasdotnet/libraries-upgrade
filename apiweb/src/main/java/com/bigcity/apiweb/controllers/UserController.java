package com.bigcity.apiweb.controllers;

import com.bigcity.apiweb.dto.BookDTO;
import com.bigcity.apiweb.dto.BookingDTO;
import com.bigcity.apiweb.dto.LoginDTO;
import com.bigcity.apiweb.dto.UserDTO;
import com.bigcity.apiweb.entity.User;
import com.bigcity.apiweb.services.interfaces.IUserService;
import com.bigcity.apiweb.specifications.UserCriteria;
import com.bigcity.apiweb.specifications.UserSpecification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
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
        @ApiResponse(code = 201, message = "utilisateur enregistré", response = BookDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie", response = BookDTO.class),
        @ApiResponse(code = 409, message = "l'utilisateur existe déjà dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @PostMapping("/api/librarian/users")
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

    @ApiOperation("Récupère un utilisateur grâce à son ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = BookingDTO.class),
        @ApiResponse(code = 404, message = "l'utilisateur n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/users/{id}")
    public ResponseEntity showUser(@PathVariable("id") int id) {

        log.debug("showUser() id: {}", id);

        return ResponseEntity.ok(iUserService.getUser(Long.valueOf(id)));

    }

    @ApiOperation("Mettre à jour un utilisateur à partir de son ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "utilisateur a été mis à jour", response = BookingDTO.class),
        @ApiResponse(code = 404, message = "l'utilisateur n'existe pas dans la base"),
        @ApiResponse(code = 400, message = "erreur de saisie", response = BookingDTO.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @PutMapping("/api/user/users/{id}")
    public ResponseEntity updateUser(@PathVariable("id") int id, @Valid @RequestBody UserDTO userDTO) throws Exception {

        log.debug("updateUser()");

        return ResponseEntity.ok().body(iUserService.edit(userDTO));

    }

    @ApiOperation("Récupère l'ensemble des utilisateurs de la base en fonction du prénom, du nom, de l'email et du role")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = UserDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie", response = UserDTO.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/librarian/users")
    public Page<User> showAllUsersByCriteria(@RequestBody UserCriteria userCriteria, int page, int size) throws Exception {

        log.debug("showAllUsersByCriteria", userCriteria);

        return iUserService.getAllUsersByCriteria(userCriteria, page, size);
    }

    @ApiOperation("Récupère un utilisateur grâce à son email")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = BookingDTO.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/users")
    public ResponseEntity getUserByEmail(@RequestParam String email) {

        return ResponseEntity.ok(iUserService.getUserByEmail(email));

    }

    @ApiOperation("S'identifier auprès du système")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = LoginDTO.class),
        @ApiResponse(code = 400, message = "erreur de saisie", response = LoginDTO.class)})
    @PostMapping("/api/user/login")
    public ResponseEntity login(@Valid @RequestBody LoginDTO loginDTO) throws Exception {

        log.debug("login()");

        return ResponseEntity.ok(iUserService.login(loginDTO));

    }
}
