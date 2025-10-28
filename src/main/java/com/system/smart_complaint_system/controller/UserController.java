package com.system.smart_complaint_system.controller;

import com.system.smart_complaint_system.dto.UserLoginDto;
import com.system.smart_complaint_system.dto.UserRegisterDTO;
import com.system.smart_complaint_system.dto.UserResponseDTO;
import com.system.smart_complaint_system.entity.User;
import com.system.smart_complaint_system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    // register user

//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody UserRegisterDTO dto) {
//        try {
//            UserResponseDTO response = service.register(dto);
//            log.info("User registered successfully: {}", dto.getEmail());
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
//        } catch (RuntimeException e) {
//            log.error("Error registering user: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }


    // find user by email
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            UserResponseDTO response = service.getUserByEmail(email);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // login

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserLoginDto dto) {
//        try {
//            UserResponseDTO response = service.login(dto);
//            log.info("User logged in successfully: {}", dto.getEmail());
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException e) {
//            log.error("Login failed for user: {}", dto.getEmail());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//        }
//    }



    // get all the user

    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<UserResponseDTO> users = service.getAll();
        return ResponseEntity.ok(users);
    }

    // update user


    @PutMapping("/{email}")
    public ResponseEntity<?> updateUserByEmail(@PathVariable String email, @RequestBody UserRegisterDTO dto) {
        try {
            UserResponseDTO updatedUser = service.update(dto, email);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // delete user

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        try {
            service.deleteUser(email);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
