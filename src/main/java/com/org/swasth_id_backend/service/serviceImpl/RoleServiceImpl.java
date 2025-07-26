package com.org.swasth_id_backend.service.serviceImpl;

import com.org.swasth_id_backend.dto.RoleDto;
import com.org.swasth_id_backend.entity.Role;
import com.org.swasth_id_backend.entity.User;
import com.org.swasth_id_backend.exception.ResourceNotFoundException;
import com.org.swasth_id_backend.mapper.RoleMapper;
import com.org.swasth_id_backend.repo.RoleRepo;
import com.org.swasth_id_backend.repo.UserRepo;
import com.org.swasth_id_backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roleList = roleRepo.findAll();
        List<RoleDto> roleDtoList = new ArrayList<>();
        roleList.forEach(role-> roleDtoList.add(RoleMapper.roleToRoleDto(role)));
        return roleDtoList;
    }

    @Override
    public Role getRoleById(UUID id) throws ResourceNotFoundException {
        Optional<Role> role = roleRepo.findById(id);
        if(role.isEmpty()){
            throw new ResourceNotFoundException("Role with this id not found "+ id);
        }
        return role.get();
    }

    @Override
    public Role getRoleByName(String roleName) throws ResourceNotFoundException {
        Optional<Role> role = roleRepo.findByRole(roleName);
        if(role.isEmpty()){
            throw new ResourceNotFoundException("Role with this id not found "+ roleName);
        }
        return role.get();
    }

    @Override
    public void deleteRoleById(UUID id) throws ResourceNotFoundException {
        Optional<Role> role = roleRepo.findById(id);
        if(role.isEmpty()){
            throw new ResourceNotFoundException("Role with this id not found "+ id);
        }
        roleRepo.deleteById(id);
    }

    @Override
    public void createRole(Role role) {
        roleRepo.save(role);
    }

    @Override
    public void assignRole(UUID userId, UUID roleId) throws ResourceNotFoundException {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("User with this id "+ userId+" is not found");
        }
        Optional<Role> role = roleRepo.findById(roleId);
        if(role.isEmpty()){
            throw new ResourceNotFoundException("Role with this id "+ roleId+" is not found");
        }
        Set<Role> roles = user.get().getRoles();
        roles.add(role.get());
        user.get().setRoles(roles);
        userRepo.save(user.get());
    }

    @Override
    public void unAssignRole(UUID userId, UUID roleId) throws ResourceNotFoundException {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("User with this id "+ userId+" is not found");
        }
        Optional<Role> role = roleRepo.findById(roleId);
        if(role.isEmpty()){
            throw new ResourceNotFoundException("Role with this id "+ roleId+" is not found");
        }
        Set<Role> roles = user.get().getRoles();

        boolean roleRemoved = roles.remove(role.get());

        if (!roleRemoved) {
            throw new ResourceNotFoundException("Role with id " + roleId + " is not assigned to user with id " + userId);
        }

        userRepo.save(user.get());
    }


}
