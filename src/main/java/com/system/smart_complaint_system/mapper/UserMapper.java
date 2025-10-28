package com.system.smart_complaint_system.mapper;

import com.system.smart_complaint_system.dto.UserLoginDto;
import com.system.smart_complaint_system.dto.UserRegisterDTO;
import com.system.smart_complaint_system.dto.UserResponseDTO;
import com.system.smart_complaint_system.entity.User;

public class UserMapper {


    // Convert Registration DTO → Entity
    public static User toEntity(UserRegisterDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setRole(dto.getRole());
        return user;
    }

    //  Convert Entity → Response DTO
    public static UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
    }

    //  Optional: Convert Login DTO → Entity (useful if needed later)
    public static User toEntity(UserLoginDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }
}
