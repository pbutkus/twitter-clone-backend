package com.pbutkus.chirper.controller;

import com.pbutkus.chirper.service.ChirpService;
import com.pbutkus.chirper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pbutkus.chirper.entity.User;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ChirpService chirpService;

    @Autowired
    public UserController(UserService userService, ChirpService chirpService) {
        this.userService = userService;
        this.chirpService = chirpService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User newUser = userService.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        return new ResponseEntity<>(userService.findById(uuid), HttpStatus.OK);
    }

    @GetMapping("/sub/{sub}")
    @ResponseBody
    public ResponseEntity<User> getUserBySub(@PathVariable String sub) {
        User user = userService.findBySub(sub);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
