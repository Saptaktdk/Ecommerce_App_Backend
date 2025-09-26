package com.app.ecommerce;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(userService.fetchAllUsers());
        //return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {

        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("User added successfully");
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody User updatedUser) {
        boolean updated = userService.updateUser(id, updatedUser);
        if (updated) {
            return ResponseEntity.ok("User updated successfully");
        }

        return ResponseEntity.notFound().build();

    }
}
