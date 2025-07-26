package com.org.swasth_id_backend.service.serviceImpl;


import com.org.swasth_id_backend.dto.UserDto;
import com.org.swasth_id_backend.entity.Role;
import com.org.swasth_id_backend.entity.User;
import com.org.swasth_id_backend.exception.ResourceNotFoundException;
import com.org.swasth_id_backend.exception.UserNotFoundException;
import com.org.swasth_id_backend.mapper.UserMapper;
import com.org.swasth_id_backend.repo.RoleRepo;
import com.org.swasth_id_backend.repo.UserRepo;
import com.org.swasth_id_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Override
	public User getUserByUserName(String userName) throws ResourceNotFoundException {
		Optional<User> user = userRepo.findByUsername(userName);
		if (user.isEmpty()) {
			throw new ResourceNotFoundException("User not found with this username " + userName);
		}
		return user.get();
	}

	@Override
	public UserDto getUserDtoByUserName(String username) throws ResourceNotFoundException {
		Optional<User> user = userRepo.findByUsername(username);
		if (user.isEmpty()) {
			throw new ResourceNotFoundException("User not found with this username " + username);
		}
		UserDto userDto = UserMapper.userToUserDto(user.get());
		return userDto;
	}

	@Override
	public UserDto getUserByEmail(String email) throws UserNotFoundException {
		Optional<User> user = userRepo.findByEmail(email);
		if (user.isEmpty()) {
			throw new UserNotFoundException("User with this email not found " + email);
		}
		return UserMapper.userToUserDto(user.get());
	}

	@Override
	public List<UserDto> getAllUserList() {
		List<User> userList = userRepo.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		userList.forEach(user -> userDtoList.add(UserMapper.userToUserDto(user)));
		return userDtoList;
	}

	@Override
	public UserDto getUserById(UUID userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with this " + userId + " id"));
		return UserMapper.userToUserDto(user);
	}

	@Override
	public UserDto updateUserData(UserDto userDto) throws UserNotFoundException {
		Optional<User> user = userRepo.findById(userDto.getUserId());
		if (user.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		User exisitingUser = user.get();
		exisitingUser.setIsActive(userDto.getIsActive());
		exisitingUser.setFirstName(userDto.getFirstName());
		exisitingUser.setLastName(userDto.getLastName());
		exisitingUser.setIsVerified(userDto.getIsVerified());
		exisitingUser.setPhoneNumber(userDto.getPhoneNumber());
		exisitingUser.setIsOtpVerified(userDto.getIsOtpVerified());
		return UserMapper.userToUserDto(userRepo.save(exisitingUser));
	}

	@Override
	public UserDto updateUserPartialData(UserDto userDto) throws UserNotFoundException {
		Optional<User> user = userRepo.findById(userDto.getUserId());
		if (user.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		User exisitingUser = user.get();
		exisitingUser.setFirstName(userDto.getFirstName());
		exisitingUser.setLastName(userDto.getLastName());
		exisitingUser.setPhoneNumber(userDto.getPhoneNumber());
		User updatedUser = userRepo.save(exisitingUser);
		return UserMapper.userToUserDto(updatedUser);
	}

	@Override
	public boolean deleteUser(UUID userId) throws UserNotFoundException {
		Optional<User> user = userRepo.findById(userId);
		if (user.isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		user.get().setRoles(null);
		userRepo.delete(user.get());
		return true;
	}

	@Override
	public void initializeRolesAndAdmin() {
		try {
			Role adminRole;
			Role userRole;

			// Check and create ROLE_ADMIN
			Optional<Role> optionalAdminRole = roleRepo.findByRole("ROLE_ADMIN");
			if (optionalAdminRole.isPresent()) {
				adminRole = optionalAdminRole.get();
			} else {
				adminRole = Role.builder()
						.role("ROLE_ADMIN")
						.description("Admin can Handle all the services and functionality")
						.build();
				adminRole = roleRepo.save(adminRole);
			}

			// Check and create ROLE_USER
			Optional<Role> optionalUserRole = roleRepo.findByRole("ROLE_USER");
			if (optionalUserRole.isPresent()) {
				userRole = optionalUserRole.get();
			} else {
				userRole = Role.builder()
						.role("ROLE_USER")
						.description("User role for patients/doctors")
						.build();
				userRole = roleRepo.save(userRole);
			}

			// Create admin user if not exists
			if (userRepo.findByEmail("admin@example.com").isEmpty()) {
				User admin = User.builder()
						.username("admin")
						.firstName("Admin")
						.lastName("User")
						.email("admin@example.com")
						.phoneNumber("1234567890")
						.age(30)
						.bloodGroup("O+")
						.password(encoder.encode("admin"))
						.roles(Set.of(adminRole))
						.isActive(true)
						.isVerified(true)
						.isOtpVerified(true)
						.build();

				userRepo.save(admin);
			}
		} catch (Exception e) {
			System.err.println("⚠️ Error initializing roles or users: " + e.getMessage());
		}
	}

}
