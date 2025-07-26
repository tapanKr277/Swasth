package com.org.swasth_id_backend.service;


import com.org.swasth_id_backend.dto.RoleDto;
import com.org.swasth_id_backend.entity.Role;
import com.org.swasth_id_backend.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    public List<RoleDto> getAllRoles();
    public Role getRoleById(UUID id) throws ResourceNotFoundException;
    public Role getRoleByName(String roleName) throws ResourceNotFoundException;
    public void deleteRoleById(UUID id) throws ResourceNotFoundException;
    public void createRole(Role role);

    public void assignRole(UUID userId, UUID roleId) throws ResourceNotFoundException;
    public void unAssignRole(UUID userId, UUID roleId) throws ResourceNotFoundException;

}
