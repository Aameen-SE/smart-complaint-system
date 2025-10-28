package com.system.smart_complaint_system.service;

import com.system.smart_complaint_system.dto.UserLoginDto;
import com.system.smart_complaint_system.dto.UserRegisterDTO;
import com.system.smart_complaint_system.dto.UserResponseDTO;
import com.system.smart_complaint_system.entity.User;
import com.system.smart_complaint_system.mapper.UserMapper;
import com.system.smart_complaint_system.repository.UserRepo;
import com.system.smart_complaint_system.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    //registerUSer

//    public User register(User user){
//        if(repo.existsByEmail(user.getEmail())){
//            throw new RuntimeException("User with same email exists");
//        }
//        log.info("User is registered successfully");
//        return repo.save(user);
//    }

    public UserResponseDTO register(UserRegisterDTO dto) {
        if (repo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("User with same email exists");
        }

        User user = UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = repo.save(user);
        log.info("User registered successfully with email: {}", savedUser.getEmail());

        return UserMapper.toResponseDTO(savedUser);
    }

    // Login


    public String login(UserLoginDto dto) {
        User user = repo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + dto.getEmail()));

        //  Check password using encoder
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        log.info(" User login successful for email: {}", dto.getEmail());

        //  Generate JWT token
        return jwtUtil.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities(user.getRole().name())
                        .build()
        );
    }
    // find User By Email

    public UserResponseDTO getUserByEmail(String email) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return UserMapper.toResponseDTO(user);
    }

    // find list of user

    public List<UserResponseDTO> getAll() {
        log.info("Fetching all users");
        return repo.findAll()
                .stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    //Update user

    public UserResponseDTO update(UserRegisterDTO dto, String email) {
        User updatedUser = repo.findByEmail(email)
                .map(u -> {
                    u.setFullName(dto.getFullName());
                    u.setAddress(dto.getAddress());
                    u.setEmail(dto.getEmail());
                    u.setPhone(dto.getPhone());
                    return repo.save(u);
                })
                .orElseThrow(() -> new RuntimeException("No user found with email: " + email));

        log.info("User updated successfully for email: {}", email);
        return UserMapper.toResponseDTO(updatedUser);
    }


    public void deleteUser(String email) {
        repo.findByEmail(email)
                .ifPresentOrElse(
                        repo::delete,
                        () -> {
                            throw new RuntimeException("User not found with email: " + email);
                        }
                );
        log.info("User deleted successfully with email: {}", email);
    }
}
