package com.victorjesus.security.auth.controller;

import com.victorjesus.security.auth.domain.users.User;
import com.victorjesus.security.auth.dto.users.UserRequestLogin;
import com.victorjesus.security.auth.dto.users.UserRequestCreate;
import com.victorjesus.security.auth.dto.users.UserResponseCreate;
import com.victorjesus.security.auth.repository.UserRepository;
import com.victorjesus.security.auth.service.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequestLogin request){
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());

        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new UserResponseCreate(token));
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<UserResponseCreate> createUser(@RequestBody @Valid UserRequestCreate request){
        if(userRepository.findByLogin(request.login()) != null) return ResponseEntity.badRequest().build();

        String passwordEncrypted = new BCryptPasswordEncoder().encode(request.password());
        User newUser = new User(request.login(), passwordEncrypted, request.role());


        userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }


}
