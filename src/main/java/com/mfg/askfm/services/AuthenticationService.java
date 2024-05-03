package com.mfg.askfm.services;

import com.mfg.askfm.dtos.AuthenticationRegisterResponse;
import com.mfg.askfm.dtos.AuthenticationResponse;
import com.mfg.askfm.entities.User;
import com.mfg.askfm.repos.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationRegisterResponse register(User request) {

        Optional<User> tempUser = userRepository.findByUserName(request.getUsername());
        if (tempUser.isPresent())
            return new AuthenticationRegisterResponse("User already exists. Please choose another username.", 409);

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user = userRepository.save(user);

        //String token = jwtService.generateToken(user);
        return new AuthenticationRegisterResponse("Success", 200);
    }

    public AuthenticationResponse authenticate(User request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        User user = userRepository.findByUserName(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token, user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());
    }
}
