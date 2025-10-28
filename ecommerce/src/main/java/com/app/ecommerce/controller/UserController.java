package com.app.ecommerce.controller;

import com.app.ecommerce.dto.UserRequest;
import com.app.ecommerce.dto.UserResponse;
import com.app.ecommerce.model.User;
import com.app.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id) {

        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return ResponseEntity.ok("User added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody UserRequest updatedUserRequest) {
        boolean updated = userService.updateUser(id, updatedUserRequest);
        if (updated) {
            return ResponseEntity.ok("User updated successfully");
        }

        return ResponseEntity.notFound().build();

    }
}
