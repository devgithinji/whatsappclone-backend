package com.densoft.whatsappclone.controller;

import com.densoft.whatsappclone.dto.LoginRequest;
import com.densoft.whatsappclone.model.User;
import com.densoft.whatsappclone.response.AuthResponse;
import com.densoft.whatsappclone.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.createUser(user));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest re) {
        return ResponseEntity.ok(authService.loginUser(re));
    }
}
