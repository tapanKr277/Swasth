package com.org.swasth_id_backend.service.serviceImpl;

import com.org.swasth_id_backend.dto.ChangePasswordDto;
import com.org.swasth_id_backend.dto.UserDto;
import com.org.swasth_id_backend.entity.User;
import com.org.swasth_id_backend.exception.*;
import com.org.swasth_id_backend.mapper.UserMapper;
import com.org.swasth_id_backend.repo.UserRepo;
import com.org.swasth_id_backend.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleService roleService;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByEmail(email);
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("User Not Found with this email "+ email);
		}
		return new UserDetailsImpl(user.get());
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Transactional
	public void addUser(User user) throws ResourceNotFoundException {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setIsVerified(false);
		user.setIsOtpVerified(false);
		user.getRoles().add(roleService.getRoleByName("ROLE_USER"));
		userRepo.save(user);
	}

	@Transactional
	public void addUserByOAuth(User user) throws ResourceNotFoundException {
		user.setPassword(encoder.encode(UUID.randomUUID().toString()));
		user.setIsVerified(true);
		user.setIsOtpVerified(false);
		user.getRoles().add(roleService.getRoleByName("ROLE_USER"));
		userRepo.save(user);
	}

	@Transactional
	public void deleteUser(UUID userId) {
		userRepo.deleteById(userId);
	}

	@Transactional
	public void updatePasswordByOldPassword(ChangePasswordDto changePasswordDto, String username) throws WrongPasswordException, PasswordMisMatchException, SamePasswordException {
		Optional<User> user = userRepo.findByUsername(username);
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("User with this username: " + username + " not found");
		}

		if (!encoder.matches(changePasswordDto.getOldPassword(), user.get().getPassword())) {
			throw new WrongPasswordException("Wrong password");
		}

		if (!changePasswordDto.getPassword().equals(changePasswordDto.getConfirmPassword())) {
			throw new WrongPasswordException("Password and confirm password should be the same");
		}

		if(encoder.encode(changePasswordDto.getPassword()).equals(user.get().getPassword())){
			throw new SamePasswordException("Same password old password and new password should be different");
		}

		User updatedUser = user.get();
		updatedUser.setPassword(encoder.encode(changePasswordDto.getPassword()));

		// Save the updated user
		userRepo.save(updatedUser);
	}

	@Transactional
	public void changePassword(ChangePasswordDto changePasswordDto) throws UserNotFoundException, PasswordMisMatchException {
		Optional<User> user = userRepo.findByEmail(changePasswordDto.getEmail());
		if(user.isEmpty()){
			throw new UserNotFoundException("User with this email not found "+ changePasswordDto.getEmail());
		}
		User updateUser = user.get();
		if(!changePasswordDto.getPassword().equals(changePasswordDto.getConfirmPassword())){
			throw new PasswordMisMatchException("Password and confirm Password should be same");
		}

		updateUser.setPassword(encoder.encode(changePasswordDto.getPassword()));
		userRepo.save(updateUser);
	}


	@Transactional
	public UserDto updateUserIsVerified(String email, Boolean isVerified){
		User user = userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User with this email "+ email+" not found"));
		user.setIsVerified(isVerified);
		User updatedUser = userRepo.save(user);
		return UserMapper.userToUserDto(updatedUser);

	}

	@Transactional
	public void updateIsOtpVerified(String email, boolean isOtpVerified) {
		User user = userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User with this email "+ email+" not found"));
		user.setIsOtpVerified(isOtpVerified);
		User updatedUser = userRepo.save(user);
		log.error(String.valueOf(updatedUser.getIsOtpVerified()));
	}

	@Transactional
	public void updateOtpLastUpdate(String email){
		User user = userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User with this email "+ email+" not found"));
		user.setOtpLastUpdate(LocalDateTime.now());
		userRepo.save(user);
	}
}
