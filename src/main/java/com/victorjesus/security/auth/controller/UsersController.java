package com.victorjesus.security.auth.controller;

import com.victorjesus.security.auth.config.SecurityConfiguration;
import com.victorjesus.security.auth.domain.exception.UserNotFoundException;
import com.victorjesus.security.auth.domain.users.User;
import com.victorjesus.security.auth.dto.users.UserResponseDTO;
import com.victorjesus.security.auth.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
@Tag(name = "user", description = "Controller get and delete users.")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete user from id", description = "Delete user")
    @ApiResponse(responseCode = "204", description = "User deleted with success!")
    @ApiResponse(responseCode = "404", description = "User not found!")
    @ApiResponse(responseCode = "500", description = "An unexpected error occurred!")
    @SecurityRequirement(name = SecurityConfiguration.SECURITY)
    public ResponseEntity<?> deleteUserById(@PathVariable String id){
        var userOptional = Optional.of(userRepository.getReferenceById(id)).orElseThrow(() -> new UserNotFoundException("Not possible to find user."));

        userRepository.deleteById(userOptional.getId());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", description = "Get user passing Id")
    @ApiResponse(responseCode = "200", description = "Find user with success!")
    @ApiResponse(responseCode = "404", description = "User not found!")
    @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    @SecurityRequirement(name = SecurityConfiguration.SECURITY)
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id){
        Optional<User> userOptional = Optional.of(userRepository.getReferenceById(id));

        return userOptional
                .map(u -> ResponseEntity.ok(new UserResponseDTO(u)))
                .orElseThrow(() -> new UserNotFoundException("Not possible to find user."));
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Get all users")
    @ApiResponse(responseCode = "200", description = "Find users with success!")
    @ApiResponse(responseCode = "500", description = "An unexpected error occurred.")
    @SecurityRequirement(name = SecurityConfiguration.SECURITY)
    public ResponseEntity<List<UserResponseDTO>> getUsersById(){
        List<User> users = userRepository.findAll();

        return ResponseEntity.ok(
                users.stream()
                        .map(UserResponseDTO::new)
                        .toList()
        );
    }
}
