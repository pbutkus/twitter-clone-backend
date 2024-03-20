package com.pbutkus.chirper.controller;

import com.pbutkus.chirper.entity.TestUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }

    @GetMapping("/hello/user")
    public ResponseEntity<TestUser> getUser() {
        return new ResponseEntity<>(new TestUser("test"), HttpStatus.OK);
    }

}
