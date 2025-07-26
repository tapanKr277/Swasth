package com.org.swasth_id_backend.service;


import com.org.swasth_id_backend.dto.UserDto;
import com.org.swasth_id_backend.entity.User;
import com.org.swasth_id_backend.exception.ResourceNotFoundException;
import com.org.swasth_id_backend.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {


    public User getUserByUserName(String userName) throws ResourceNotFoundException;

    UserDto getUserDtoByUserName(String username) throws ResourceNotFoundException;

    UserDto getUserByEmail(String email) throws UserNotFoundException;

    List<UserDto> getAllUserList();

    UserDto getUserById(UUID userId);

    UserDto updateUserData(UserDto userDto) throws UserNotFoundException;

    UserDto updateUserPartialData(UserDto userDto) throws UserNotFoundException;

    boolean deleteUser(UUID userId) throws UserNotFoundException;

    void initializeRolesAndAdmin();
}
