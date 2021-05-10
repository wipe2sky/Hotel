package com.kurtsevich.hotel.controller;

import com.kurtsevich.hotel.api.service.IUserService;
import com.kurtsevich.hotel.dto.RoleWithoutUsersDto;
import com.kurtsevich.hotel.dto.UserDto;
import com.kurtsevich.hotel.dto.UserRoleDto;
import com.kurtsevich.hotel.model.security.Role;
import com.kurtsevich.hotel.model.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final IUserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/users")
    public ResponseEntity<Void> addUser(@RequestBody UserDto userDto){
        userService.register(userDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/users/role")
    public ResponseEntity<Void> addUserRole(@Valid @RequestBody UserRoleDto userRoleDto){
        userService.addUserRole(userRoleDto);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/users/{id}/role")
    public ResponseEntity<List<RoleWithoutUsersDto>> getUserRole(@PathVariable Integer id){
        return ResponseEntity.ok(userService.getById(id).getRoles());

    }
    @DeleteMapping("/users/role")
    public ResponseEntity<Void> deleteUserRole(@Valid @RequestBody UserRoleDto userRoleDto){
        userService.deleteUserRole(userRoleDto);
        return ResponseEntity.noContent().build();
    }
}
