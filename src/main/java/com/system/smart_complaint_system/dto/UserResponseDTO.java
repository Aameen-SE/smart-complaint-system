package com.system.smart_complaint_system.dto;


import com.system.smart_complaint_system.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Role role;


}
