package com.victorjesus.security.auth.controller;

import com.victorjesus.security.auth.config.SecurityConfiguration;
import com.victorjesus.security.auth.domain.exception.UserNotFoundException;
import com.victorjesus.security.auth.domain.users.User;
import com.victorjesus.security.auth.dto.users.UserRequestLogin;
import com.victorjesus.security.auth.dto.users.UserRequestCreate;
import com.victorjesus.security.auth.dto.users.UserResponseDTO;
import com.victorjesus.security.auth.dto.users.UserResponseLogin;
import com.victorjesus.security.auth.repository.UserRepository;
import com.victorjesus.security.auth.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("auth")
@Tag(name = "user", description = "Controller to save and login with users data")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "Login with users data", description = "Login method")
    @ApiResponse(responseCode = "200", description = "User logged")
    @ApiResponse(responseCode = "400", description = "All fields must be filled.")
    @ApiResponse(responseCode = "404", description = "User not found!")
    @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequestLogin request){
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());

        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new UserResponseLogin(token));
    }

    @PostMapping("/create")
    @Transactional
    @Operation(summary = "Create user with param data", description = "Create user")
    @ApiResponse(responseCode = "201", description = "User created with success!")
    @ApiResponse(responseCode = "400", description = "All fields must be filled.")
    @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestCreate request, UriComponentsBuilder uriComponentsBuilder){
        if(userRepository.findByLogin(request.login()) != null) return ResponseEntity.badRequest().build();

        String passwordEncrypted = new BCryptPasswordEncoder().encode(request.password());
        User newUser = new User(request.login(), passwordEncrypted, request.role());

        userRepository.save(newUser);

        var uri = uriComponentsBuilder
                .path("/users/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new UserResponseDTO(newUser));
    }

    @DeleteMapping("/users/delete/{id}")
    @Transactional
    @Operation(summary = "Delete user from id", description = "Delete user")
    @ApiResponse(responseCode = "204", description = "User deleted with success!")
    @ApiResponse(responseCode = "400", description = "All fields must be filled.")
    @ApiResponse(responseCode = "404", description = "User not found!")
    @ApiResponse(responseCode = "500", description = "An unexpected error occurred!")
    @SecurityRequirement(name = SecurityConfiguration.SECURITY)
    public ResponseEntity<?> deleteUserById(@PathVariable String id){
        var userOptional = Optional.of(userRepository.getReferenceById(id)).orElseThrow(() -> new UserNotFoundException("Not possible to find user."));

        userRepository.deleteById(userOptional.getId());

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/users/{id}")
    @Operation(summary = "Get user by id", description = "Get user passing Id")
    @ApiResponse(responseCode = "200", description = "Find user with success!")
    @ApiResponse(responseCode = "400", description = "All fields must be filled.")
    @ApiResponse(responseCode = "404", description = "User not found!")
    @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    @SecurityRequirement(name = SecurityConfiguration.SECURITY)
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id){
        Optional<User> userOptional = Optional.of(userRepository.getReferenceById(id));

        return userOptional
                .map(u -> ResponseEntity.ok(new UserResponseDTO(u)))
                .orElseThrow(() -> new UserNotFoundException("Not possible to find user."));
    }


}
