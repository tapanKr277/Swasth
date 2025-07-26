package com.org.swasth_id_backend.mapper;


import com.org.swasth_id_backend.dto.RoleDto;
import com.org.swasth_id_backend.entity.Role;

public class RoleMapper {

    // Method to map RoleDto to Role entity
    public static Role roleDtoToRole(RoleDto roleDto) {
        if (roleDto == null) {
            return null;
        }

        Role role = new Role();
        role.setId(roleDto.getRoleId());
        role.setRole(roleDto.getRole());
        role.setDescription(roleDto.getDescription());
        role.setCreatedAt(roleDto.getCreatedAt());
        role.setLastUpdate(roleDto.getLastUpdate());

        return role;
    }

    // Method to map Role entity to RoleDto
    public static RoleDto roleToRoleDto(Role role) {
        if (role == null) {
            return null;
        }

        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(role.getId());
        roleDto.setRole(role.getRole());
        roleDto.setDescription(role.getDescription());
        roleDto.setCreatedAt(role.getCreatedAt());
        roleDto.setLastUpdate(role.getLastUpdate());

        return roleDto;
    }
}

