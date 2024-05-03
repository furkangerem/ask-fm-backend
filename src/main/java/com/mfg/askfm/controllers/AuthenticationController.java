package com.mfg.askfm.controllers;

import com.mfg.askfm.dtos.AuthenticationRegisterResponse;
import com.mfg.askfm.dtos.AuthenticationResponse;
import com.mfg.askfm.entities.User;
import com.mfg.askfm.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationRegisterResponse> register(@RequestBody User request) {
        AuthenticationRegisterResponse response = authenticationService.register(request);
        return switch (response.getHttpStatus()) {
            case 200 -> ResponseEntity.ok(response);
            case 409 -> ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            default -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        };
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
