package com.photoalbum.controller;

import com.photoalbum.service.AuthService;
import com.photoalbum.login.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.photoalbum.login.LoginRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse tokens = authService.login(request.username, request.password);
        return ResponseEntity.ok(tokens);
    }
}
