package com.pbutkus.chirper.controller;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.pbutkus.chirper.model.request.EmailPasswordAuthRequest;
import com.pbutkus.chirper.model.request.UserInfoRequest;
import com.pbutkus.chirper.service.Auth0Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final Auth0Service authService;

    @Autowired
    public AuthController(Auth0Service authService) {
        this.authService = authService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login/email-password")
    @ResponseBody
    public ResponseEntity<Object> loginEmailPassword(@RequestBody EmailPasswordAuthRequest emailPasswordAuthRequest) {
        try {
            TokenHolder authResponse = authService.emailPasswordAuth(emailPasswordAuthRequest.email(), emailPasswordAuthRequest.password());
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/user-info")
    @ResponseBody
    public ResponseEntity<Object> getUserInfo(@RequestBody UserInfoRequest userInfoRequest) throws Auth0Exception {
        try {
            return new ResponseEntity<>(authService.getUserData(userInfoRequest.accessToken()), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @PostMapping("/refresh-token/{token}")
    @ResponseBody
    public ResponseEntity<Object> refreshToken(@PathVariable String token) {
        try {
            return new ResponseEntity<>(authService.getRefreshToken(token), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad Request");
        }

    }

}
