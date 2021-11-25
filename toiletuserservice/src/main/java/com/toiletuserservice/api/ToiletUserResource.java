package com.toiletuserservice.api;

import com.toiletuserservice.domain.Role;
import com.toiletuserservice.domain.ToiletUser;
import com.toiletuserservice.service.ToiletUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class ToiletUserResource {
    private final ToiletUserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<ToiletUser>> getToiletUsers() {
        return ResponseEntity.ok(userService.getToiletUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<ToiletUser> saveToiletUsers(@RequestBody ToiletUser toiletUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveToiletUser(toiletUser));
    }
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

}
@Data
class RoleToUserForm {
    private String userName;
    private String roleName;

}