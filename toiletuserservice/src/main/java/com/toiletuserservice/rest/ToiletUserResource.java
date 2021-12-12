package com.toiletuserservice.rest;


import com.jasb.entities.Role;
import com.jasb.entities.ToiletUser;
import com.toiletuserservice.service.ToiletUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Restcontoller for userservice using Spring boot
 * base url i /api/
 * Written by JASB
 */

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class ToiletUserResource {
    private final ToiletUserService userService;

    /**
     * Endpoint for returning all users from the database
     * endpoint is /api/users abd accepts a REST GET request
     * @return ResponseEntity<List<ToiletUser>>> a list of toiletusers in the form of a ResponseEntity
     * Accessible to a user who sends a JWT with the Role ROLE_SUPER_ADMIN
     */

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<List<ToiletUser>> getToiletUsers() {
        return ResponseEntity.ok(userService.getToiletUsers());
    }

    /**
     * Endpoint  for saving a new ToiletUser to the database
     * endpoint is /api/user/save and accepts a REST POST request
     * expects a JSON representation of a ToiletUser
     * Open to all users
     * @param toiletUser
     * @return ResponseEntity<ToiletUser> a conformation and representation of the new user
     */
    @PostMapping("/user/save")
    public ResponseEntity<String> saveToiletUsers(@RequestBody ToiletUser toiletUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        userService.saveToiletUser(toiletUser);
        // TODO får nu tillbaka "User created" även om det inte funkade
        return ResponseEntity.created(uri).body("User created");
    }

    /**
     * Endpoint for saving a new Role to the database
     * endpoint is /api/role/save and accepts at REST POST request
     * expects a JSON representation of a Role
     * Accessible to a user who sends a JWT with the Role ROLE_SUPER_ADMIN
     * @param role
     * @return ResponseEntity<Role> a conformation and representation of the new user
     */
    @PostMapping("/role/save")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
    /**
     * Endpoint for adding a new Role to a ToiletUser
     * endpoint is /api/role/addtouser and accepts at REST POST request
     * expects a JSON object containing a valid username and a valid rolename
     * Accessible to a user who sends a JWT with the Role ROLE_SUPER_ADMIN
     * @param form a form representation of a username and rolename
     * @return ResponseEntity confirming the creation
     */
    @PostMapping("/role/addtouser")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRolename());
        return ResponseEntity.ok().build();
    }

}

/**
 * A helper class to add a new role to a user
 */
@Data
class RoleToUserForm {
    private String username;
    private String rolename;
}